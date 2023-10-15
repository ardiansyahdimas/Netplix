package com.xsis.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsis.core.data.source.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GenreDao {

    @Query("SELECT * FROM genre where id IN (:genreIds)")
    fun getGenreMovieById(genreIds:List<Int>): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genre")
    fun getGenresMovie(): Flow<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genreList: List<GenreEntity>)

}