package com.xsis.netplix.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xsis.core.data.Resource
import com.xsis.core.domain.model.GenreModel
import com.xsis.core.domain.model.MovieModel
import com.xsis.core.domain.model.ReviewMovieModel
import com.xsis.core.domain.model.VideoMovieModel
import com.xsis.core.domain.usecase.MovieUseCase
import com.xsis.core.utils.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (private val movieUseCase: MovieUseCase) : ViewModel() {
    var resultValueNowPlaying : LiveData<Resource<List<MovieModel>>>? =null
    var resultValuePopuler : LiveData<Resource<List<MovieModel>>>? =null
    var resultValueTopRated : LiveData<Resource<List<MovieModel>>>? =null
    var resultValueUpcoming : LiveData<Resource<List<MovieModel>>>? =null
    var resultReviewsMovie : LiveData<Resource<List<ReviewMovieModel>>>?  = null
    var resultGenreMovie : LiveData<Resource<List<GenreModel>>>?  = null
    var resultVideosMovie : LiveData<Resource<List<VideoMovieModel>>>?  = null
    var resultValueSearch : LiveData<Resource<List<MovieModel>>>? =null

    val getCarouselsMovie = movieUseCase.getCarouselsMovie().asLiveData()

    fun searchMovie(query:String) {
        resultValueSearch = movieUseCase.searchMovie(query).asLiveData()
    }

    fun getMovies(type:String, page:Int) {
        when(type) {
            Config.NOW_PLAYING_MOVIES -> resultValueNowPlaying =
                movieUseCase.getMovies(type, page).asLiveData()

            Config.POPULAR_MOVIES -> resultValuePopuler =
                movieUseCase.getMovies(type, page).asLiveData()

            Config.TOP_RATED_MOVIES -> resultValueTopRated =
                movieUseCase.getMovies(type, page).asLiveData()

            else -> resultValueUpcoming = movieUseCase.getMovies(type, page).asLiveData()
        }
    }

    fun getReviewsMovieByMovieId(movieId:Int) {
        resultReviewsMovie = movieUseCase.getReviewsMovieByMovieId(movieId).asLiveData()
    }

    val getGenresMovie = movieUseCase.getGenresMovie().asLiveData()

    fun getGenreMovieById(genreId:List<Int>) {
        resultGenreMovie = movieUseCase.getGenreMovieId(genreId).asLiveData()
    }

    fun getVideosMovieByMovieId(movieId: Int) {
        resultVideosMovie = movieUseCase.getVideosMovieByMovieId(movieId).asLiveData()
    }
}