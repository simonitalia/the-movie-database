package com.italiasimon.themoviedatabase.ui.movieDetail

import android.app.Application
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.FavoriteMoviesRepository
import com.italiasimon.themoviedatabase.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    app: Application,
    val movie: Movie
) : BaseViewModel(app) {

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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val repository: FavoriteMoviesRepository = FavoriteMoviesRepository(app)

    suspend fun getMovieById() {
        repository.getMovieById(
            movie.id,
            onSuccess = { isFavorite ->
                _isFavorite.postValue(isFavorite)
            },
            onFailure = {}
        )
    }

    suspend fun updateFavorites() {

        when (_isFavorite.value) {

            // remove favorite
            true -> {
                repository.removeFavorite(
                    movie = movie,
                    onSuccess = {
                        _isFavorite.postValue(false)
                        showToast()

                        /*
                            * FOR TESTING *
                            * to trigger failure,
                            * comment out above and uncomment below
                         */

                        // _isFavorite.value = false
                    },
                    onFailure = {
                        showSnackBarError()
                    }
                )
            }

            //add favorite
            else -> {
                repository.saveFavorite(
                    movie = movie,
                    onSuccess = {
                        _isFavorite.postValue(true)
                        showToast()

                        /*
                            * FOR TESTING *
                            * to trigger failure,
                            * comment out above and uncomment below
                         */

                        // _isFavorite.value = true
                    },
                    onFailure = {
                        showSnackBarError()
                    }
                )
            }
        }
    }

    /*
     * Navigation
     */

    fun showFavoritesFragment() {
        _navigationCommand.value = true
    }

    fun onShowFavoritesFragmentCompleted() {
        _navigationCommand.value = false
    }
}