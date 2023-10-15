package com.xsis.core.data.source.local

import com.xsis.core.data.source.local.entity.CarouselEntity
import com.xsis.core.data.source.local.entity.GenreEntity
import com.xsis.core.data.source.local.entity.MovieEntity
import com.xsis.core.data.source.local.entity.ReviewMovieEntity
import com.xsis.core.data.source.local.entity.VideoMovieEntity
import com.xsis.core.data.source.local.room.CarouselDao
import com.xsis.core.data.source.local.room.GenreDao
import com.xsis.core.data.source.local.room.MovieDao
import com.xsis.core.data.source.local.room.ReviewMovieDao
import com.xsis.core.data.source.local.room.VideoMovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val carouselDao: CarouselDao,
    private val movieDao: MovieDao,
    private val reviewMovieDao: ReviewMovieDao,
    private val genreMovieDao: GenreDao,
    private val videoMovieDao: VideoMovieDao
) {
//    CAROUSEL MOVIE
    fun getCarouselsMovie():Flow<List<CarouselEntity>> = carouselDao.getCarouselsMovie()
    suspend fun insertCarouselsMovie(carouselList: List<CarouselEntity>) = carouselDao.insertCarouselsMovie(carouselList)

//    MOVIE
    fun getMoviesByType(type:String):Flow<List<MovieEntity>> = movieDao.getMoviesByType(type)
    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)
    suspend fun deleteMovieByType(type:String) = movieDao.deleteMovieByType(type)
    fun doesMovieExist(type: String) = movieDao.doesMovieExist(type)

//    REVIEW MOVIE
    fun getReviewsMovieByMovieId(movieId:Int):Flow<List<ReviewMovieEntity>> = reviewMovieDao.getMoviesByMovieId(movieId)
    suspend fun insertReviewsMovie(reviewList: List<ReviewMovieEntity>) = reviewMovieDao.insertReviewsMovie(reviewList)

//   GENRE MOVIE
    fun getGenreMovieById(genreIds:List<Int>):Flow<List<GenreEntity>> = genreMovieDao.getGenreMovieById(genreIds)
    fun getGenresMovie():Flow<List<GenreEntity>> = genreMovieDao.getGenresMovie()
    suspend fun insertGenres(genreList: List<GenreEntity>) = genreMovieDao.insertGenres(genreList)

//  VIDEO MOVIE
    fun getVideosMovieByMovieId(movieId: Int):Flow<List<VideoMovieEntity>> = videoMovieDao.getVideoMoviesByMovieId(movieId)
    suspend fun insertVideosMovie(videoList: List<VideoMovieEntity>) = videoMovieDao.insertVideosMovie(videoList)

}