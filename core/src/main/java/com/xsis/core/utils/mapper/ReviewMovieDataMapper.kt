package com.xsis.core.utils.mapper

import com.xsis.core.data.source.local.entity.ReviewMovieEntity
import com.xsis.core.data.source.remote.response.ResultsReviewItem
import com.xsis.core.domain.model.ReviewMovieModel

object ReviewMovieDataMapper {
    fun mapResponsesToEntities(movieId:Int,input: List<ResultsReviewItem>): List<ReviewMovieEntity> {
        val reviewList = ArrayList<ReviewMovieEntity>()
        input.map {
            val review = ReviewMovieEntity(
                id = it.id,
                movieId = movieId,
                rating = it.authorDetails?.rating,
                avatarPath = it.authorDetails?.avatarPath,
                author = it.author,
                content = it.content,
                created_at  = it.createdAt
            )
            reviewList.add(review)
        }
        return reviewList
    }

    fun mapEntitiesToDomain(input: List<ReviewMovieEntity>): List<ReviewMovieModel> =
        input.map {
            ReviewMovieModel(
                id = it.id,
                movieId = it.movieId,
                rating = it.rating,
                avatarPath = it.avatarPath,
                author = it.author,
                content = it.content,
                created_at  = it.created_at
            )
        }
}