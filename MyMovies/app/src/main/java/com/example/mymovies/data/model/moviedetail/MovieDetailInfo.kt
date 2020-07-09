package com.example.mymovies.data.model.moviedetail

data class MovieDetailInfo(
    var backdropPath: String?,
    var budget: Long,
    var genres: String,
    var homepage: String?,
    var id: Int,
    var originalTitle: String,
    var overview: String,
    var posterPath: String,
    var productionCompanies: String,
    var productionCountries: String,
    var releaseDate: String,
    var spokenLanguages: String,
    var voteAverage: Float,
    var voteCount: Int
)