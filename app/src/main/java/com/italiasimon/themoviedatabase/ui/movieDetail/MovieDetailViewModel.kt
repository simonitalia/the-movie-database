package com.italiasimon.themoviedatabase.ui.movieDetail

import android.app.Application
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.FavoriteMoviesRepository

class MovieDetailViewModel(
    app: Application,
    movie: Movie
) : AndroidViewModel(app) {

    /*
     * Factory for constructing this ViewModel with constructor parameters
     */
    class Factory(val app: Application, val movie: Movie): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(app, movie) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val repository: FavoriteMoviesRepository = FavoriteMoviesRepository(app)

    init {
        _movie.value = movie
        _isFavorite.value = false
    }

    fun updateFavorites(movie: Movie) {

    }


}