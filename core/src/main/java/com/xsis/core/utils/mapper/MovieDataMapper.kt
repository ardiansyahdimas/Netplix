package com.xsis.core.utils.mapper

import com.xsis.core.data.source.local.entity.MovieEntity
import com.xsis.core.data.source.remote.response.ResultsMovieItem
import com.xsis.core.domain.model.MovieModel

object MovieDataMapper {
    fun mapResponsesToEntities(type:String,input: List<ResultsMovieItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
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
                type = type

            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieModel> =
        input.map {
            MovieModel(
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
                type = it.type,
                genreIds = it.genreIds
            )
        }
}