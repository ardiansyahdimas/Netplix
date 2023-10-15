package com.xsis.core.utils.mapper

import com.xsis.core.data.source.local.entity.VideoMovieEntity
import com.xsis.core.data.source.remote.response.ResultsVideoItem
import com.xsis.core.domain.model.VideoMovieModel

object VideoMovieDataMapper {
    fun mapResponsesToEntities(movieId:Int,input: List<ResultsVideoItem>): List<VideoMovieEntity> {
        val videoMovieList = ArrayList<VideoMovieEntity>()
        input.map {
            val video = VideoMovieEntity(
                id = it.id,
                movieId = movieId,
                official = it.official,
                site = it.site,
                size = it.size,
                name  = it.name,
                type = it.type,
                key = it.key

            )
            videoMovieList.add(video)
        }
        return videoMovieList
    }

    fun mapEntitiesToDomain(input: List<VideoMovieEntity>): List<VideoMovieModel> =
        input.map {
            VideoMovieModel(
                id = it.id,
                movieId = it.movieId,
                official = it.official,
                site = it.site,
                size = it.size,
                name  = it.name,
                type = it.type,
                key = it.key
            )
        }
}