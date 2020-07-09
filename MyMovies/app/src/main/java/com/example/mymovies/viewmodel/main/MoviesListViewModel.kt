package com.example.mymovies.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.repository.MoviesRepository
import com.example.mymovies.view.base.BaseViewModel
import javax.inject.Inject

class MoviesListViewModel
@Inject constructor(application: Application, private var repository: MoviesRepository) :
    BaseViewModel(application) {

    var movieList: LiveData<PagedList<MovieInfo>> = repository.movieList

    fun retry() {
        repository.retry()
    }

    fun refresh() {
        repository.refresh()
    }

    fun getNetworkState(): LiveData<NetworkState> = repository.getNetworkState()

    fun getRefreshState(): LiveData<NetworkState> = repository.getRefreshState()

    /*fun getMovies(page: Int): Observable<MoviesListInfo> {
        return repository.getMovies(page)
            .map { user ->
                user.movies = user.movies?.filter { item ->
                    !item.isAdult
                }
                user
            }
    }*/
}