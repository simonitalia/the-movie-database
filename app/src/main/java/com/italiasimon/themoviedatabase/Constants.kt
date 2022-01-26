package com.italiasimon.themoviedatabase

import com.italiasimon.themoviedatabase.models.Movie

object Constants {

    enum class PosterSize(val value: String) {
        W_92("w92"),
        W_154("w154"),
        W_185("w185"),
        W_342("w342"),
        W_500("w500"),
        W_780("w780"),
        ORIGINAL("original")
    }

    fun getPosterImagePath(size: PosterSize = PosterSize.W_342, urlString: String) : String {
        val posterImageUrl = "https://image.tmdb.org/t/p/"
        return posterImageUrl + size.value + urlString
    }

}