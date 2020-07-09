package com.example.mymovies.data.model.movielist

data class MovieInfo(
    var id: Int,
    var posterPath: String?,
    var adult: Boolean,
    var title: String,
    var voteAverage: Float,
    var overview: String,
    var releaseDate: String,
    var page: Int
)