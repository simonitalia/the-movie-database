package com.italiasimon.themoviedatabase.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.repositories.MoviesRepository
import com.italiasimon.themoviedatabase.tmdbClient.TmdbApi
import com.italiasimon.themoviedatabase.ui.base.BaseViewModel

class MainViewModel(
    app: Application,
) : BaseViewModel(app) {

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
        private const val TAG = "MainViewModel"
    }

    enum class MovieListCategory {
        ALL, POPULAR, TOP_RATED, UPCOMING
    }

    // popular movies fetched
    private val _apiStatusPopular = MutableLiveData<TmdbApi.ApiStatus>()
    val apiStatusPopular: LiveData<TmdbApi.ApiStatus>
        get() = _apiStatusPopular

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _showErrorPopular = MutableLiveData<Boolean>()
    val showErrorPopular: LiveData<Boolean>
        get() = _showErrorPopular

    // top rated movies fetched
    private val _apiStatusTopRated = MutableLiveData<TmdbApi.ApiStatus>()
    val apiStatusTopRated: LiveData<TmdbApi.ApiStatus>
        get() = _apiStatusTopRated

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies


    // ui messages
    private val _showToastPopular = MutableLiveData<Boolean>()
    val showToastPopular: LiveData<Boolean>
        get() = _showToastPopular

    private val _showToastTopRated = MutableLiveData<Boolean>()
    val showToastTopRated: LiveData<Boolean>
        get() = _showToastTopRated

    private val _showErrorTopRated = MutableLiveData<Boolean>()
    val showErrorTopRated: LiveData<Boolean>
        get() = _showErrorTopRated

    // user tapped movie
    private val _selectedMovie = MutableLiveData<Movie?>()
    val selectedMovie: LiveData<Movie?>
        get() = _selectedMovie

    private val repository: MoviesRepository = MoviesRepository()

    init {
        updateMovies(MainViewModel.MovieListCategory.ALL)
    }

    /*
     * Fetch movies data
     */
    fun updateMovies(category: MovieListCategory) {
        when (category) {

            /*
                * check if any Movies list is empty
                * since this method is called if fetch of any movie list fails
             */
            MovieListCategory.ALL -> {
                if (_popularMovies.value == null || _popularMovies.value?.isEmpty() == true) {
                    updatePopularMovies()
                }

                if (_topRatedMovies.value == null || _topRatedMovies.value?.isEmpty() == true) {
                    updateTopRatedMovies()
                }
            }

            MovieListCategory.POPULAR -> {
                updatePopularMovies()
            }

            MovieListCategory.TOP_RATED -> {
                updatePopularMovies()
            }

            else -> return
        }
    }

    private fun updatePopularMovies(page: Int = 1) {
        _apiStatusPopular.value = TmdbApi.ApiStatus.LOADING

        repository.updateMovies(
            TmdbApi.Endpoint.POPULAR,
            page,
            onSuccess =  {
                _apiStatusPopular.value = TmdbApi.ApiStatus.DONE
                onMoviesUpdated(it, TmdbApi.Endpoint.POPULAR)
            },
            onError = {
                _apiStatusPopular.value = TmdbApi.ApiStatus.ERROR
                showError(TmdbApi.Endpoint.POPULAR)
            }
        )
    }

    private fun updateTopRatedMovies(page: Int = 1) {
        _apiStatusTopRated.value = TmdbApi.ApiStatus.LOADING

        repository.updateMovies(
            TmdbApi.Endpoint.TOP_RATED,
            page,
            onSuccess =  {
                _apiStatusTopRated.value = TmdbApi.ApiStatus.DONE
                onMoviesUpdated(it, TmdbApi.Endpoint.TOP_RATED)
            },
            onError = {
                _apiStatusTopRated.value = TmdbApi.ApiStatus.ERROR
                showError(TmdbApi.Endpoint.TOP_RATED)
            }
        )
    }

    private fun onMoviesUpdated(movies: List<Movie>, endpoint: TmdbApi.Endpoint) {

        when (endpoint) {
            TmdbApi.Endpoint.POPULAR -> {
                _popularMovies.value = movies
                _showToastPopular.value = true
            }

            TmdbApi.Endpoint.TOP_RATED -> {
                _topRatedMovies.value = movies
                _showToastTopRated.value = true
            }

            else -> return
        }
    }

    fun showToastCompleted(category: MovieListCategory) {

        when (category) {
            MovieListCategory.POPULAR -> _showToastPopular.value = false
            MovieListCategory.TOP_RATED -> _showToastTopRated.value = false
            else -> return
        }
    }

    private fun showError(endpoint: TmdbApi.Endpoint) {

        when (endpoint) {
            TmdbApi.Endpoint.POPULAR -> _showErrorPopular.value = true
            TmdbApi.Endpoint.TOP_RATED -> _showErrorTopRated.value = true
            else -> return
        }
    }

    fun showErrorCompleted(category: MovieListCategory) {

        when (category) {
            MovieListCategory.POPULAR -> _showErrorPopular.value = false
                MovieListCategory.TOP_RATED -> _showErrorTopRated.value = false
            else -> return
        }
    }

    /*
     * Sort movies
     */
    fun sortMovies(ascending: Boolean, category: MovieListCategory) {

        when (category) {
            MovieListCategory.POPULAR -> {
                if (ascending) {
                    _popularMovies.value = _popularMovies.value?.sortedBy { movie ->
                        movie.title
                    }
                } else {
                    _popularMovies.value = _popularMovies.value?.sortedByDescending { movie ->
                        movie.title
                    }
                }
            }

            else -> return
        }
    }

    /*
     * Navigation
     */

    fun onSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun showMovieDetailFragmentComplete() {
        _selectedMovie.value = null // prevent subsequent navigation
    }
}