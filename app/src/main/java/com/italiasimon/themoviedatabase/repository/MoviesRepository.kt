package com.italiasimon.themoviedatabase.repository

import android.util.Log
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.tmdbClient.TmdbApi
import com.italiasimon.themoviedatabase.tmdbClient.TmdbGetMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepository {

    companion object {
        private const val TAG = "MoviesRepository"
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private val api: TmdbApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TmdbApi::class.java)
    }

    fun getMovies(
        endpoint: TmdbApi.Endpoint,
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {

        when (endpoint) {

            // fetch popular movies
            TmdbApi.Endpoint.POPULAR -> {
                api.getPopularMovies(page = page)
                    .enqueue(object : Callback<TmdbGetMoviesResponse> {
                        override fun onResponse(
                            call: Call<TmdbGetMoviesResponse>,
                            response: Response<TmdbGetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                // on successful fetch
                                if (responseBody != null) {
                                    onSuccess(responseBody.movies)

                                // on unsuccessful fetch
                                } else {
                                    onError()
                                }
                            }
                        }

                        // on fetch error
                        override fun onFailure(call: Call<TmdbGetMoviesResponse>, t: Throwable) {
                            Log.e(TAG, "Error: onFailure: ${t.localizedMessage}", t)
                            onError()
                        }
                    })
            }

            // fetch top rated
            TmdbApi.Endpoint.TOP_RATED-> {
                api.getTopRatedMovies(page = page)
                    .enqueue(object : Callback<TmdbGetMoviesResponse> {
                        override fun onResponse(
                            call: Call<TmdbGetMoviesResponse>,
                            response: Response<TmdbGetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                // on successful fetch
                                if (responseBody != null) {
                                    onSuccess(responseBody.movies)

                                    // on unsuccessful fetch
                                } else {
                                    onError()
                                }
                            }
                        }

                        // on fetch error
                        override fun onFailure(call: Call<TmdbGetMoviesResponse>, t: Throwable) {
                            Log.e(TAG, "Error: onFailure: ${t.localizedMessage}", t)
                            onError()
                        }
                    })
            }

            // fetch upcoming
            TmdbApi.Endpoint.UPCOMING-> {
                api.getUpcomingMovies(page = page)
                    .enqueue(object : Callback<TmdbGetMoviesResponse> {
                        override fun onResponse(
                            call: Call<TmdbGetMoviesResponse>,
                            response: Response<TmdbGetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                // on successful fetch
                                if (responseBody != null) {
                                    onSuccess(responseBody.movies)

                                    // on unsuccessful fetch
                                } else {
                                    onError()
                                }
                            }
                        }

                        // on fetch error
                        override fun onFailure(call: Call<TmdbGetMoviesResponse>, t: Throwable) {
                            Log.e(TAG, "Error: onFailure: ${t.localizedMessage}", t)
                            onError()
                        }
                    })
            }
        }
    }
}