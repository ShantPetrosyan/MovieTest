package com.example.mymovies.viewmodel.detail

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.model.moviedetail.MovieDetailInfo
import com.example.mymovies.data.repository.MovieDetailRepository
import com.example.mymovies.view.base.BaseViewModel
import javax.inject.Inject

class MovieDetailViewModel
@Inject constructor(application: Application, private var repository: MovieDetailRepository) :
    BaseViewModel(application) {

    var movieList: LiveData<MovieDetailInfo> = repository.movieDetailInfo
    val networkState: LiveData<NetworkState> = repository.networkState

    fun getMovieDetail(movieId: Int) {
        repository.getMovieDetail(movieId)
    }

    fun retry() {
        repository.retry()
    }
}