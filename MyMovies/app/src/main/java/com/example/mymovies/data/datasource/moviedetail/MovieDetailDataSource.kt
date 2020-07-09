package com.example.mymovies.data.datasource.moviedetail

import androidx.lifecycle.MutableLiveData
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.model.moviedetail.MovieDetailInfo
import com.example.mymovies.data.net.ApiConstants
import com.example.mymovies.data.net.MoviesApi
import com.example.mymovies.data.response.moviedetail.MovieDetailResponse
import com.example.mymovies.view.mapper.MoviesMapper
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieDetailDataSource(
    private val movieService: MoviesApi,
    private val compositeDisposable: CompositeDisposable
) {
    val networkState = MutableLiveData<NetworkState>()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable) })
            )
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    fun getMovieDetail(
        movieId: Int,
        callback: (MovieDetailInfo) -> Unit
    ) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)

        //get the initial users from the api
        compositeDisposable.add(
            movieService.getMovieDetailInfo(movieId, ApiConstants.API_KEY)
                .map { response: MovieDetailResponse ->
                    MoviesMapper.convertToModel(response)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieDetail ->
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    callback(movieDetail)
                }, { throwable ->
                    // keep a Completable for future retry
                    setRetry(Action { getMovieDetail(movieId, callback) })
                    val error = NetworkState.error(throwable.message)
                    // publish the error
                    networkState.postValue(error)
                })
        )
    }
}