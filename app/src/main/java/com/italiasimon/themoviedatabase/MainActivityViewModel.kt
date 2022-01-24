package com.italiasimon.themoviedatabase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repository.MoviesRepository

class MainActivityViewModel(
    private val repository: MoviesRepository = MoviesRepository()
) : ViewModel() {

    companion object {
        private const val TAG = "MainActivityViewModel"

    }

    private val _movies = MutableLiveData<List<Movie>>()
    val movies = _movies.value ?: emptyList()

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