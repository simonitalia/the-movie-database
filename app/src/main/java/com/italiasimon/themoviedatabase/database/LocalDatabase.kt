package com.italiasimon.themoviedatabase.database

import android.content.Context
import androidx.room.*

/**
 * Singleton of Local (Room) tmdbDatabase.
 */

@Database(entities = [MovieDto::class], version = 1, exportSchema = false)
abstract class TmdbDatabase: RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}

object LocalDatabase {
    private lateinit var INSTANCE: TmdbDatabase // prevent initialization of multiple instances

    fun getInstance(context: Context): TmdbDatabase {
        synchronized(TmdbDatabase::class.java) { // use synchronized for thread safety

            //  check instance not already initialized before creating instance
            if (!::INSTANCE.isInitialized) {

                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TmdbDatabase::class.java,
                    "tmdbDatabase.db"
                ).build()
            }
        }

        return INSTANCE
    }
}

/**
 * Data Access Object for querying tables containing movies
 */
@Dao
interface MoviesDao {

    /*
     * favorite_movies_table queries
     */

    @Query("SELECT * FROM favorite_movies_table")
    suspend fun getAllMovies(): List<MovieDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDto)

    @Query("SELECT * FROM favorite_movies_table where id = :movieId")
    suspend fun getMovieById(movieId: Long): MovieDto?

    @Query("DELETE FROM favorite_movies_table where id = :movieId")
    suspend fun deleteMovie(movieId: Long)

    @Query("DELETE FROM favorite_movies_table")
    suspend fun deleteAllMovies()
}




