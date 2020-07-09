package com.example.mymovies.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.datasource.moviespaging.MovieDataSourceFactory
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.net.MoviesApi
import com.example.mymovies.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    application: Application,
    retrofit: Retrofit
) : BaseViewModel(application) {
    private var moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    var movieList: LiveData<PagedList<MovieInfo>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private val sourceFactory: MovieDataSourceFactory
    private var config: PagedList.Config

    init {
        sourceFactory =
            MovieDataSourceFactory(
                compositeDisposable,
                moviesApi
            )
        config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        movieList = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun retry() {
        sourceFactory.moviesDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.moviesDataSourceLiveData.value!!.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(
        sourceFactory.moviesDataSourceLiveData
    ) { it.networkState }

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap(
        sourceFactory.moviesDataSourceLiveData
    ) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}