package com.italiasimon.themoviedatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.italiasimon.themoviedatabase.models.Movie

/**
 * Immutable model class for a Movie objects to compile with Room
 *
 * @param id                id of movie object
 * @param title             title of movie object
 * @param overview          overview of movie object
 * @param posterPath        posterPath of movie object
 * @param backdropPath      backdrop of movie object
 * @param rating            rating of movie object
 * @param releaseDate       releaseDate of movie object
 */

@Entity(tableName = "favorite_movies_table")
data class MovieDto(
    @PrimaryKey @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "posterPath")
    var posterPath: String,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String,

    @ColumnInfo(name = "rating")
    var rating: Float,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,
)




