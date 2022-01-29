package com.italiasimon.themoviedatabase.tmdbClient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    companion object {
        private const val API_KEY = "592e57a23d9bb7229dbf9e08f25818b4"
    }

    enum class ApiStatus {
        LOADING, ERROR, DONE
    }

    enum class Endpoint {
        POPULAR, TOP_RATED, UPCOMING
    }

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ) : Call<TmdbGetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ) : Call<TmdbGetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Call<TmdbGetMoviesResponse>
}