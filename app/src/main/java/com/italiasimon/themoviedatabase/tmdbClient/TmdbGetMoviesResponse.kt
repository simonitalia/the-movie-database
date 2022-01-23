package com.italiasimon.themoviedatabase.tmdbClient

import com.google.gson.annotations.SerializedName
import com.italiasimon.themoviedatabase.models.Movie

data class TmdbGetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
