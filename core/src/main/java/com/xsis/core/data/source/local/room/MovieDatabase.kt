package com.xsis.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xsis.core.data.source.local.entity.CarouselEntity
import com.xsis.core.data.source.local.entity.GenreEntity
import com.xsis.core.data.source.local.entity.MovieEntity
import com.xsis.core.data.source.local.entity.ReviewMovieEntity
import com.xsis.core.data.source.local.entity.VideoMovieEntity

@Database(entities = [
    MovieEntity::class,
    ReviewMovieEntity::class,
    GenreEntity::class,
    CarouselEntity::class,
    VideoMovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun reviewMovieDao(): ReviewMovieDao
    abstract fun genreMovieDao(): GenreDao
    abstract fun carouselMovieDao(): CarouselDao
    abstract fun videoMovieDao(): VideoMovieDao
}