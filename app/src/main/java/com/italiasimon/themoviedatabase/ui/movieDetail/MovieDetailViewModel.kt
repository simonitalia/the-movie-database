package com.italiasimon.themoviedatabase.ui.movieDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.FavoriteMoviesRepository

class MovieDetailViewModel(
    app: Application,
    val movie: Movie
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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    private val _showErrorMessage = MutableLiveData<Boolean>()
    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage

    private val repository: FavoriteMoviesRepository = FavoriteMoviesRepository(app)

    init {
        //TODO set value
//        _isFavorite.value =
    }

    suspend fun updateFavorites() {

        when (_isFavorite.value) {

            // remove favorite
            true -> {
                repository.removeFavorite(
                    movie = movie,
                    onSuccess = {
                        onFavoriteUpdated(false)
                        Log.i(TAG, ".updateFavorites: Movie deleted")
                    },
                    onFailure = {}
                )
            }

            //add favorite
            else -> {
                repository.saveFavorite(
                    movie = movie,
                    onSuccess = {
                        onFavoriteUpdated(true)
                        Log.i(TAG, ".updateFavorites: Movie saved")
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun onFavoriteUpdated(isFavorite: Boolean) {
        _isFavorite.postValue(isFavorite)
            // use liveData.postValue(value) to update live data value from background
    }
}