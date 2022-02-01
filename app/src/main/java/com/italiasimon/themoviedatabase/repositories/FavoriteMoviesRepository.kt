package com.italiasimon.themoviedatabase.repositories

import android.app.Application
import android.util.Log
import com.italiasimon.themoviedatabase.asMovieDto
import com.italiasimon.themoviedatabase.asMovieModel
import com.italiasimon.themoviedatabase.database.LocalDatabase
import com.italiasimon.themoviedatabase.database.TmdbDatabase
import com.italiasimon.themoviedatabase.models.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteMoviesRepository(
    app: Application
) {

    companion object {
        private const val TAG = "FavoriteMoviesRepo"
    }

    private val database: TmdbDatabase = LocalDatabase.getInstance(app)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun saveFavorite(
        movie: Movie,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {

        withContext(ioDispatcher) {

            try {
                database.moviesDao().saveMovie(movie.asMovieDto())
                onSuccess()

            } catch (e: Exception) {
                onFailure()
                Log.i(TAG, ".updateFavorites: Movie save error: $e")
            }
        }
    }

    suspend fun removeFavorite(
        movie: Movie,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {

        withContext(ioDispatcher) {

            try {
                database.moviesDao().deleteMovie(movie.id)
                onSuccess()

            } catch (e: Exception) {
                onFailure()
                Log.i(TAG, ".updateFavorites: Delete error: $e")
            }
        }
    }

    suspend fun getMovieById(
        movieId: Long,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit
    ) {

        withContext(ioDispatcher) {
            try {
                val movie = database.moviesDao().getMovieById(movieId)?.asMovieModel()
                val isMovieFound = (movie?.id == movieId)
                onSuccess(isMovieFound)

                 val logMessage = if (isMovieFound)  {
                     "Movie found"
                } else {
                    "Movie not found"
                }

                Log.i(TAG, ".getMovieById: $logMessage")

            } catch(e: Exception) {
                onFailure()
                Log.i(TAG, ".getMovieById: Fetching movie error: $e")
            }
        }
    }
}