package com.italiasimon.themoviedatabase.tmdbClient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")

    fun getPopularMovies(
        @Query("api_key") apiKey: String = "592e57a23d9bb7229dbf9e08f25818b4",
        @Query("page") page: Int
    ) : Call<TmdbGetMoviesResponse>
}