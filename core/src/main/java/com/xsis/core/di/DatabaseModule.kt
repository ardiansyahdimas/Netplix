package com.xsis.core.di

import android.content.Context
import androidx.room.Room
import com.xsis.core.data.source.local.room.CarouselDao
import com.xsis.core.data.source.local.room.GenreDao
import com.xsis.core.data.source.local.room.MovieDao
import com.xsis.core.data.source.local.room.MovieDatabase
import com.xsis.core.data.source.local.room.ReviewMovieDao
import com.xsis.core.data.source.local.room.VideoMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("xsis".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "MovieApp.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideReviewMovieDao(database: MovieDatabase): ReviewMovieDao = database.reviewMovieDao()

    @Provides
    fun provideGenresMovieDao(database: MovieDatabase): GenreDao = database.genreMovieDao()

    @Provides
    fun provideCarouselMovieDao(database: MovieDatabase): CarouselDao = database.carouselMovieDao()

    @Provides
    fun provideVideoMovieDao(database: MovieDatabase): VideoMovieDao = database.videoMovieDao()
}