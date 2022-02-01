package com.italiasimon.themoviedatabase.ui.movieDetail

import android.app.Application
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.FavoriteMoviesRepository
import kotlinx.coroutines.launch

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

    private val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    private val _showSnackbarError = MutableLiveData<Boolean>()
    val showSnackbarError: LiveData<Boolean>
        get() = _showSnackbarError

    private val repository: FavoriteMoviesRepository = FavoriteMoviesRepository(app)

    init  {
        // check if movie is a favorite
        viewModelScope.launch {

            repository.getMovieById(
                movie.id,
                onSuccess = { isFavorite ->
                    _isFavorite.postValue(isFavorite)
                },
                onFailure = {}
            )
        }
    }

    suspend fun updateFavorites() {

        when (_isFavorite.value) {

            // remove favorite
            true -> {
                repository.removeFavorite(
                    movie = movie,
                    onSuccess = {
                        _isFavorite.postValue(false)
                        _showToast.postValue(true)

                        /*
                            * FOR TESTING *
                            * to trigger failure,
                            * comment out above and uncomment below
                         */

                        // _isFavorite.value = false
                    },
                    onFailure = {
                        _showSnackbarError.postValue(true)
                    }
                )
            }

            //add favorite
            else -> {
                repository.saveFavorite(
                    movie = movie,
                    onSuccess = {
                        _isFavorite.postValue(true)
                        _showToast.postValue(true)

                        /*
                            * FOR TESTING *
                            * to trigger failure,
                            * comment out above and uncomment below
                         */

                        // _isFavorite.value = true
                    },
                    onFailure = {
                        _showSnackbarError.postValue(true)
                    }
                )
            }
        }
    }

    fun showSnackBarErrorCompleted() {
        _showSnackbarError.value = false
    }

    fun showToastCompleted() {
        _showToast.value = false
    }
}