package com.italiasimon.themoviedatabase.ui.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.FavoriteMoviesRepository
import com.italiasimon.themoviedatabase.ui.base.BaseViewModel
import kotlinx.coroutines.runBlocking

class FavoritesViewModel(
    app: Application,
) : BaseViewModel(app) {

    /*
     * Factory for constructing this ViewModel with constructor parameters
     */
    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoritesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    companion object {
        private const val TAG = "FavoritesViewModel"
    }

    private val repository = FavoriteMoviesRepository(app)

    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    // user tapped movie
    private val _selectedFavoriteMovie = MutableLiveData<Movie?>()
    val selectedFavoriteMovie: LiveData<Movie?>
        get() = _selectedFavoriteMovie

    init {
        getFavorites()
    }

    fun getFavorites() = runBlocking {
        repository.getFavoriteMovies(
            onSuccess = { favoriteMovies ->
                _favoriteMovies.postValue(favoriteMovies)
                showToast()
            },
            onFailure = {
                showSnackBarError()
            }
        )
    }

    /*
     * Navigation
     */

    fun onSelectedFavoriteMovie(favoriteMovie: Movie) {
        _selectedFavoriteMovie.value = favoriteMovie
    }

    fun showMovieDetailFragmentComplete() {
        _selectedFavoriteMovie.value = null
    }
}