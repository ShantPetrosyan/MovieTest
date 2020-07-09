package com.example.mymovies.data.response.moviedetail

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var budget: Long,
    var genres: List<Genre>,
    var homepage: String?,
    var id: Int,
    @SerializedName("original_title")
    var originalTitle: String,
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage>,
    @SerializedName("vote_average")
    var voteAverage: Float,
    @SerializedName("vote_count")
    var voteCount: Int
)