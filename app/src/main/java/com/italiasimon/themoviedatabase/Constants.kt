package com.italiasimon.themoviedatabase

object Constants {

    /*
     * Movie Image sizes
     */

    private const val IMAGE_URL_ENDPOINT = "https://image.tmdb.org/t/p/"

    enum class PosterSize(val value: String) {
        W_92("w92"),
        W_154("w154"),
        W_185("w185"),
        W_342("w342"),
        W_500("w500"),
        W_780("w780"),
        ORIGINAL("original"); //w: 526

        companion object {
            fun getImagePath(size: PosterSize, urlString: String): String {
                return IMAGE_URL_ENDPOINT + size.value + urlString
            }
        }
    }

    enum class BackdropSize(val value: String) {
        W_300("w300"),
        W_780("w780"),
        W_1280("w1280"),
        ORIGINAL("original");

        companion object {
            fun getImagePath(size: BackdropSize, urlString: String): String {
                return IMAGE_URL_ENDPOINT + size.value + urlString
            }
        }
    }
}