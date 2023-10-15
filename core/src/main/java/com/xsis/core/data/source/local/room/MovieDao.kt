package com.xsis.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsis.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies where type=:type")
    fun getMoviesByType(type:String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieList: List<MovieEntity>)

    @Query("DELETE FROM  movies WHERE type=:type")
    suspend fun deleteMovieByType(type: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE type=:type LIMIT 1)")
    fun doesMovieExist(type: String): Boolean
}