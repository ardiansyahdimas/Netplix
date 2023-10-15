package com.xsis.core.domain.usecase

import com.xsis.core.data.Resource
import com.xsis.core.domain.model.CarouselModel
import com.xsis.core.domain.model.GenreModel
import com.xsis.core.domain.model.MovieModel
import com.xsis.core.domain.model.ReviewMovieModel
import com.xsis.core.domain.model.VideoMovieModel
import com.xsis.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
    MovieUseCase {
    override fun searchMovie(query: String): Flow<Resource<List<MovieModel>>>  = movieRepository.searchMovie(query)
    override fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>> = movieRepository.getMovies(type, page)
    override fun getReviewsMovieByMovieId(movieId: Int): Flow<Resource<List<ReviewMovieModel>>> = movieRepository.getReviewsMovieByMovieId(movieId)
    override fun getGenresMovie(): Flow<Resource<List<GenreModel>>>  = movieRepository.getGenresMovie()
    override fun getGenreMovieId(genreIds: List<Int>): Flow<Resource<List<GenreModel>>>  = movieRepository.getGenreMovieId(genreIds)
    override fun  getCarouselsMovie(): Flow<Resource<List<CarouselModel>>> = movieRepository.getCarouselsMovie()
    override fun getVideosMovieByMovieId(movieId: Int): Flow<Resource<List<VideoMovieModel>>> = movieRepository.getVideosMovieByMovieId(movieId)
}