package com.xsis.core.data.source.remote.network


import com.xsis.core.data.source.remote.response.GetMoviesResponse
import com.xsis.core.data.source.remote.response.ReviewsMovieResponse
import com.xsis.core.data.source.remote.response.GetGenresMovieResponse
import com.xsis.core.data.source.remote.response.GetVideoMovieResponse
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("trending/movie/day")
    suspend fun getCarousel(): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String
    ): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("movie/popular")
    suspend fun getPopulerMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): GetMoviesResponse

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewsMovieByMovieId(
        @Path("movie_id") movieId: Int
    ): ReviewsMovieResponse

    @Headers("Content-Type: application/json")
    @GET("genre/movie/list")
    suspend fun getGenresMovie(): GetGenresMovieResponse

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/videos")
    suspend fun getVideosByMovieId(
        @Path("movie_id") movieId: Int
    ): GetVideoMovieResponse
}
