package com.example.mymovies.data.response.movielist

import com.google.gson.annotations.SerializedName

open class MovieItemResponse(
    var id: Int,
    @SerializedName("poster_path")
    var posterPath: String?,
    var adult: Boolean,
    var title: String,
    @SerializedName("vote_average")
    var voteAverage: Float,
    var overview: String,
    @SerializedName("release_date")
    var releaseDate: String
)