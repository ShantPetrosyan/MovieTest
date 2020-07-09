package com.example.mymovies.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.datasource.moviedetail.MovieDetailDataSource
import com.example.mymovies.data.datasource.moviedetail.MovieDetailDataSourceFactory
import com.example.mymovies.data.model.moviedetail.MovieDetailInfo
import com.example.mymovies.data.net.MoviesApi
import com.example.mymovies.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    application: Application,
    retrofit: Retrofit
) : BaseViewModel(application) {
    private var moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    var networkState: MutableLiveData<NetworkState>

    var movieDetailInfo = MutableLiveData<MovieDetailInfo>()
    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory: MovieDetailDataSourceFactory
    private var dataStore: MovieDetailDataSource

    init {
        sourceFactory = MovieDetailDataSourceFactory(
            compositeDisposable,
            moviesApi
        )
        dataStore = sourceFactory.create()
        networkState = dataStore.networkState
    }

    fun getMovieDetail(movieId: Int) {
        dataStore.getMovieDetail(movieId) {
            movieDetailInfo.postValue(it)
        }
    }

    fun retry() {
        sourceFactory.movieDetailDataSourceLiveData.value!!.retry()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}