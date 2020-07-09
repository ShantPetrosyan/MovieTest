package com.example.mymovies.data.response.moviedetail

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    var code: String,
    var name: String
)