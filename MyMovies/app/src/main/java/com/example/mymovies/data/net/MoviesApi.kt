package com.example.mymovies.data.net

import com.example.mymovies.data.response.moviedetail.MovieDetailResponse
import com.example.mymovies.data.response.movielist.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MoviesResponse>

    @GET("3/movie/{movie_id}")
    fun getMovieDetailInfo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Single<MovieDetailResponse>
}
