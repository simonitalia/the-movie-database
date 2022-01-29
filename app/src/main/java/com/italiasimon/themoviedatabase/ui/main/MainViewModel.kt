package com.italiasimon.themoviedatabase.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repository.MoviesRepository

class MainViewModel(
    app: Application,
    private val repository: MoviesRepository = MoviesRepository()
) : AndroidViewModel(app) {

    /*
     * Factory for constructing this MainViewModel with application parameter
     */
    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    companion object {
        private const val TAG = "MainActivityViewModel"

    }

    enum class TmdbApiStatus { LOADING, ERROR, DONE }

    private val _apiStatus = MutableLiveData<TmdbApiStatus>()
    val apiStatus: LiveData<TmdbApiStatus>
        get() = _apiStatus

    // original movies list fetched from repo
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError

    init {
        getPopularMovies()
    }

    fun getPopularMovies(page: Int = 1)  {
        _apiStatus.value = TmdbApiStatus.LOADING

        repository.getPopularMovies(
            page,
            onSuccess =  {
                _apiStatus.value = TmdbApiStatus.DONE
                onPopularMoviesUpdated(it)
                showError(false)
            },
            onError = {
                _apiStatus.value = TmdbApiStatus.ERROR
                showError(true)
            }
        )
    }

    private fun onPopularMoviesUpdated(movies: List<Movie>) {
        _movies.value = movies
        _showError.value = false
    }

    private fun showError(value: Boolean) {
        _showError.value = value
    }

    fun sortMovies(ascending: Boolean) = when (ascending) {

        // sort ascending order
        true ->
        _movies.value = _movies.value?.sortedBy { movie ->
            movie.title }

        // sort descending order
        else -> _movies.value = _movies.value?.sortedByDescending { movie ->
            movie.title }
    }
}