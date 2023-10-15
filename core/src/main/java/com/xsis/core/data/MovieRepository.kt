package com.xsis.core.data

import com.xsis.core.NetworkBoundResource
import com.xsis.core.data.source.local.LocalDataSource
import com.xsis.core.data.source.remote.RemoteDataSource
import com.xsis.core.data.source.remote.network.ApiResponse
import com.xsis.core.data.source.remote.response.GenresMovieItem
import com.xsis.core.data.source.remote.response.ResultsMovieItem
import com.xsis.core.data.source.remote.response.ResultsReviewItem
import com.xsis.core.data.source.remote.response.ResultsVideoItem
import com.xsis.core.domain.model.CarouselModel
import com.xsis.core.domain.model.GenreModel
import com.xsis.core.domain.model.MovieModel
import com.xsis.core.domain.model.ReviewMovieModel
import com.xsis.core.domain.model.VideoMovieModel
import com.xsis.core.domain.repository.IMovieRepository
import com.xsis.core.utils.Config
import com.xsis.core.utils.mapper.CarouselMovieDataMapper
import com.xsis.core.utils.mapper.GenreMovieDataMapper
import com.xsis.core.utils.mapper.MovieDataMapper
import com.xsis.core.utils.mapper.ReviewMovieDataMapper
import com.xsis.core.utils.mapper.VideoMovieDataMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMovieRepository {

    override fun searchMovie(query:String): Flow<Resource<List<MovieModel>>> =
        object : NetworkBoundResource<List<MovieModel>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<List<MovieModel>>  {
                return localDataSource.getMoviesByType(Config.SEARCH_MOVIE).map { MovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean{
                GlobalScope.launch {
                    val isExistData = localDataSource.doesMovieExist(Config.SEARCH_MOVIE)
                    if (isExistData){
                        localDataSource.deleteMovieByType(Config.SEARCH_MOVIE)
                    }

                }
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> = remoteDataSource.searchMovie(query)


            override suspend fun saveCallResult(data: List<ResultsMovieItem>) {
                val movieList = MovieDataMapper.mapResponsesToEntities(Config.SEARCH_MOVIE,data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getCarouselsMovie(): Flow<Resource<List<CarouselModel>>> =
        object : NetworkBoundResource<List<CarouselModel>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<List<CarouselModel>>  {
                return localDataSource.getCarouselsMovie().map { CarouselMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<CarouselModel>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> = remoteDataSource.getCarouselsMovie()

            override suspend fun saveCallResult(data: List<ResultsMovieItem>) {
                val carouselList = CarouselMovieDataMapper.mapResponsesToEntities(data)
                localDataSource.insertCarouselsMovie(carouselList)
            }
        }.asFlow()

    override fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>> =
        object : NetworkBoundResource<List<MovieModel>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<List<MovieModel>>  {
                return localDataSource.getMoviesByType(type).map { MovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean =
                if (page < 2) {
                    data.isNullOrEmpty()
                } else {
                    true // ganti dengan true jika ingin selalu mengambil data dari internet
                }


            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> = remoteDataSource.getMovies(type, page)


            override suspend fun saveCallResult(data: List<ResultsMovieItem>) {
                val movieList = MovieDataMapper.mapResponsesToEntities(type,data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getReviewsMovieByMovieId(movieId:Int): Flow<Resource<List<ReviewMovieModel>>> =
        object : NetworkBoundResource<List<ReviewMovieModel>, List<ResultsReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewMovieModel>>  {
                return localDataSource.getReviewsMovieByMovieId(movieId).map { ReviewMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<ReviewMovieModel>?): Boolean =
//                data.isNullOrEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsReviewItem>>> = remoteDataSource.getReviewsMovieByMovieId(movieId)


            override suspend fun saveCallResult(data: List<ResultsReviewItem>) {
                val reviewMovieList = ReviewMovieDataMapper.mapResponsesToEntities(movieId,data)
                localDataSource.insertReviewsMovie(reviewMovieList)
            }
        }.asFlow()

    override fun getGenresMovie(): Flow<Resource<List<GenreModel>>> =
        object : NetworkBoundResource<List<GenreModel>, List<GenresMovieItem>>() {
            override fun loadFromDB(): Flow<List<GenreModel>>  {
                return localDataSource.getGenresMovie().map { GenreMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<GenreModel>?): Boolean =
                data.isNullOrEmpty()
//                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<GenresMovieItem>>> = remoteDataSource.getGenresMovieId()


            override suspend fun saveCallResult(data: List<GenresMovieItem>) {
                val genreMovieList = GenreMovieDataMapper.mapResponsesToEntities(data)
                localDataSource.insertGenres(genreMovieList)
            }
        }.asFlow()

    override fun getGenreMovieId(genreIds: List<Int>): Flow<Resource<List<GenreModel>>> =
        object : NetworkBoundResource<List<GenreModel>, List<GenresMovieItem>>() {
        override fun loadFromDB(): Flow<List<GenreModel>>  {
            return localDataSource.getGenreMovieById(genreIds).map { GenreMovieDataMapper.mapEntitiesToDomain(it)}
        }

        override fun shouldFetch(data: List<GenreModel>?): Boolean = false
//                true // ganti dengan true jika ingin selalu mengambil data dari internet

        override suspend fun createCall(): Flow<ApiResponse<List<GenresMovieItem>>> {
            TODO()
        }

        override suspend fun saveCallResult(data: List<GenresMovieItem>) { TODO() }
    }.asFlow()


    override fun getVideosMovieByMovieId(movieId:Int): Flow<Resource<List<VideoMovieModel>>> =
        object : NetworkBoundResource<List<VideoMovieModel>, List<ResultsVideoItem>>() {
            override fun loadFromDB(): Flow<List<VideoMovieModel>>  {
                return localDataSource.getVideosMovieByMovieId(movieId).map { VideoMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<VideoMovieModel>?): Boolean =
//                data.isNullOrEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsVideoItem>>> = remoteDataSource.getVideosMovieByMovieId(movieId)


            override suspend fun saveCallResult(data: List<ResultsVideoItem>) {
                val videoMovieList = VideoMovieDataMapper.mapResponsesToEntities(movieId,data)
                localDataSource.insertVideosMovie(videoMovieList)
            }
        }.asFlow()

}