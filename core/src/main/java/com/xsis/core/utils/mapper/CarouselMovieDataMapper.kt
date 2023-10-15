package com.xsis.core.utils.mapper

import com.xsis.core.data.source.local.entity.CarouselEntity
import com.xsis.core.data.source.remote.response.ResultsMovieItem
import com.xsis.core.domain.model.CarouselModel

object CarouselMovieDataMapper {
    fun mapResponsesToEntities(input: List<ResultsMovieItem>): List<CarouselEntity> {
        val carouselList = ArrayList<CarouselEntity>()
        input.map {
            val carousel = CarouselEntity(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdropPath,
                original_language = it.originalLanguage,
                original_title = it.originalTitle,
                genreIds = it.genreIds.toString(),
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.posterPath,
                release_date = it.releaseDate,
                title = it.title,
                video = it.video,
                vote_average = it.voteAverage,
                vote_count = it.voteCount,

            )
            carouselList.add(carousel)
        }
        return carouselList
    }


    fun mapEntitiesToDomain(input: List<CarouselEntity>): List<CarouselModel> =
        input.map {
            CarouselModel(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                genreIds = it.genreIds
            )
        }
}