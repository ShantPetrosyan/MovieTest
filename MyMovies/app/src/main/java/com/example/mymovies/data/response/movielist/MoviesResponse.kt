package com.example.mymovies.data.response.movielist

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    var page: Int,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
    var results: List<MovieItemResponse>
)