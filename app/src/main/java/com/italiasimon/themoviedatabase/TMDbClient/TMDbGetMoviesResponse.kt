package com.italiasimon.themoviedatabase.TMDbClient

import com.google.gson.annotations.SerializedName
import com.italiasimon.themoviedatabase.Models.Movie

data class TMDbGetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
