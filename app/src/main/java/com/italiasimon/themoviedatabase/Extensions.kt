package com.italiasimon.themoviedatabase

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.italiasimon.themoviedatabase.database.MovieDto
import com.italiasimon.themoviedatabase.models.Movie

/**
 * Extension helper functions
 */

fun Fragment.setTitle(title: String) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}

fun Fragment.setDisplayHomeAsUpEnabled(bool: Boolean) {
    if (activity is AppCompatActivity) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(bool)

    }
}

/**
 * Movie data model helper functions
 * Convert MovieDto (database entity) objects to domain model objects
 */

fun List<MovieDto>.asMovieList(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            rating = it.rating,
            releaseDate = it.releaseDate
        )
    }
}

fun MovieDto.asMovieModel() : Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        rating = rating,
        releaseDate = releaseDate
    )
}

/**
 * MovieDto data model helper functions
 */

//Convert Movie model object to MovieDto (database entity) object
fun Movie.asMovieDto() : MovieDto {
    return MovieDto(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        rating = rating,
        releaseDate = releaseDate
    )
}


data class MovieDtoArrayList(val movies: ArrayList<Movie>)

fun MovieDtoArrayList.asMovieDtoArray(): Array<MovieDto> {
    return movies.map {
        MovieDto(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            rating = it.rating,
            releaseDate = it.releaseDate
        )
    }.toTypedArray()
}