package com.example.mymovies.data.response.moviedetail

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var code: String,
    var name: String
)