package com.italiasimon.themoviedatabase.ui.main

import android.app.Application
import android.util.Log
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

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        getPopularMovies()
    }

    private fun getPopularMovies(page: Int = 1)  {

        repository.getPopularMovies(
            page,
            onSuccess =  {
                _movies.value = it
            },
            onError = {
                Log.i(TAG, it)
            }
        )
    }
}