package com.example.mymovies.data.datasource.moviedetail

import androidx.lifecycle.MutableLiveData
import com.example.mymovies.data.net.MoviesApi
import io.reactivex.disposables.CompositeDisposable

class MovieDetailDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val movieService: MoviesApi
) {
    val movieDetailDataSourceLiveData = MutableLiveData<MovieDetailDataSource>()

    fun create(): MovieDetailDataSource {
        return MovieDetailDataSource(
            movieService,
            compositeDisposable
        )
    }
}