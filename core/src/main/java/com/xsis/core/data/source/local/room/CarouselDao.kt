package com.xsis.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsis.core.data.source.local.entity.CarouselEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarouselDao {

    @Query("SELECT * FROM carousel")
    fun getCarouselsMovie(): Flow<List<CarouselEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarouselsMovie(carouselList: List<CarouselEntity>)
}