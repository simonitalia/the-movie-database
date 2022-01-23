package com.italiasimon.themoviedatabase.moviesRepository

import android.util.Log
import com.italiasimon.themoviedatabase.tmdbClient.TmdbApi
import com.italiasimon.themoviedatabase.tmdbClient.TmdbGetMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {
    private const val TAG = "MoviesRepository"

    private val api: TmdbApi
    private const val baseUrl = "https://api.themoviedb.org/3/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TmdbApi::class.java)
    }

    fun getPopularMovies(page: Int = 1) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<TmdbGetMoviesResponse> {
                override fun onResponse(
                    call: Call<TmdbGetMoviesResponse>,
                    response: Response<TmdbGetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.i(TAG, "Movies: ${responseBody.movies}")

                        } else {
                            Log.i(TAG, "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<TmdbGetMoviesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure", t)
                }
            })
    }
}