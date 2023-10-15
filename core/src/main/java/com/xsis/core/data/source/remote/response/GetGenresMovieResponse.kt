package com.xsis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetGenresMovieResponse(

	@field:SerializedName("genres")
	val genres: List<GenresMovieItem>? = null
)

data class GenresMovieItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int
)
