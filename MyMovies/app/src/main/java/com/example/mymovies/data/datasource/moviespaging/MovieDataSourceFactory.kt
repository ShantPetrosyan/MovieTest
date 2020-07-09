package com.example.mymovies.data.datasource.moviespaging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.net.MoviesApi
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val movieService: MoviesApi
) : DataSource.Factory<Int, MovieInfo>() {

    val moviesDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieInfo> {
        val moviesDataSource =
            MovieDataSource(
                movieService,
                compositeDisposable
            )
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}
