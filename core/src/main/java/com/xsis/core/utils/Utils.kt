package com.xsis.core.utils

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.room.TypeConverter
import com.xsis.core.domain.model.CarouselModel
import com.xsis.core.domain.model.MovieModel

object Utils {
    fun updateUI(isLoading:Boolean, progressBar: ProgressBar){
        progressBar.isVisible = isLoading
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        val numberStrings = value
            ?.replace("[", "")
            ?.replace("]", "")
            ?.split(",")
        return numberStrings?.map { it.trim().toInt() }
    }

    fun carouselToMovieModel(carouselModel: CarouselModel):MovieModel {
        return MovieModel(
            id = carouselModel.id,
            adult = carouselModel.adult,
            backdrop_path = carouselModel.backdrop_path,
            original_language = carouselModel.original_language,
            original_title = carouselModel.original_title,
            genreIds = carouselModel.genreIds,
            overview = carouselModel.overview,
            popularity = carouselModel.popularity,
            poster_path = carouselModel.poster_path,
            release_date = carouselModel.release_date,
            title = carouselModel.title,
            video = carouselModel.video,
            vote_average = carouselModel.vote_average,
            vote_count = carouselModel.vote_count,
            type = ""
        )
    }
}