package com.xsis.core.domain.usecase

import com.xsis.core.data.Resource
import com.xsis.core.domain.model.CarouselModel
import com.xsis.core.domain.model.GenreModel
import com.xsis.core.domain.model.MovieModel
import com.xsis.core.domain.model.ReviewMovieModel
import com.xsis.core.domain.model.VideoMovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun searchMovie(query:String): Flow<Resource<List<MovieModel>>>
    fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>>
    fun getReviewsMovieByMovieId(movieId:Int): Flow<Resource<List<ReviewMovieModel>>>
    fun getGenresMovie():Flow<Resource<List<GenreModel>>>
    fun getGenreMovieId(genreIds:List<Int>):Flow<Resource<List<GenreModel>>>
    fun getCarouselsMovie(): Flow<Resource<List<CarouselModel>>>
    fun getVideosMovieByMovieId(movieId:Int): Flow<Resource<List<VideoMovieModel>>>
}