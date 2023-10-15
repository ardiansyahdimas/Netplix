package com.xsis.core.data.source.remote

import com.xsis.core.data.source.remote.response.ResultsMovieItem
import com.xsis.core.data.source.remote.response.ResultsReviewItem
import com.xsis.core.utils.Config
import com.xsis.core.data.source.remote.network.ApiResponse
import com.xsis.core.data.source.remote.network.ApiService
import com.xsis.core.data.source.remote.response.GenresMovieItem
import com.xsis.core.data.source.remote.response.ResultsVideoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    val TAG = "RemoteDataSource"
    private val pageSize = 20

    suspend fun getCarouselsMovie(): Flow<ApiResponse<List<ResultsMovieItem>>> {
        return flow {
            try {
                val response =apiService.getCarousel()
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getCarousel: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovie(query:String): Flow<ApiResponse<List<ResultsMovieItem>>> {
        return flow {
            try {
                val response = apiService.searchMovie(query)
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("searchMovie: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovies(type:String, page:Int): Flow<ApiResponse<List<ResultsMovieItem>>> {
        return flow {
            try {
                val response = when(type) {
                    Config.NOW_PLAYING_MOVIES -> apiService.getNowPlayingMovies(page, pageSize)
                    Config.POPULAR_MOVIES -> apiService.getPopulerMovies(page, pageSize)
                    Config.TOP_RATED_MOVIES -> apiService.getTopRatedMovies(page, pageSize)
                    else -> apiService.getUpcomingMovies(page, pageSize)
                }
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getMovies: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReviewsMovieByMovieId(movieId:Int): Flow<ApiResponse<List<ResultsReviewItem>>> {
        return flow {
            try {
                val response = apiService.getReviewsMovieByMovieId(movieId)
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getReviewsMovieByMovieId: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGenresMovieId(): Flow<ApiResponse<List<GenresMovieItem>>> {
        return flow {
            try {
                val response = apiService.getGenresMovie()
                val dataArray = response.genres
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.genres))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getGenresMovieId: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVideosMovieByMovieId(movieId:Int): Flow<ApiResponse<List<ResultsVideoItem>>> {
        return flow {
            try {
                val response = apiService.getVideosByMovieId(movieId)
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getVideosMovieByMovieId: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }
}
