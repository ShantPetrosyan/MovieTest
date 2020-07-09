package com.example.mymovies.data.datasource.moviespaging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.net.ApiConstants
import com.example.mymovies.data.net.MoviesApi
import com.example.mymovies.data.response.movielist.MoviesResponse
import com.example.mymovies.helpers.Navigator
import com.example.mymovies.view.mapper.MoviesMapper.convertToModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

import timber.log.Timber

class MovieDataSource(
    private val movieService: MoviesApi,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Int, MovieInfo>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<MovieInfo>
    ) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        //get the initial users from the api
        compositeDisposable.add(
            movieService.getMovies(ApiConstants.API_KEY, 1)
                .map { response: MoviesResponse ->
                    response.results = response.results.filter { item ->
                        !item.adult
                    }
                    convertToModel(response)
                }
                .subscribe({ movieList ->
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(movieList)
                }, { throwable ->
                    // keep a Completable for future retry
                    setRetry(Action { loadInitial(params, callback) })
                    val error = NetworkState.error(throwable.message)
                    // publish the error
                    networkState.postValue(error)
                    initialLoad.postValue(error)
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<MovieInfo>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)

        //get the Movies from the api for specified page number
        compositeDisposable.add(
            movieService.getMovies(ApiConstants.API_KEY, params.key + 1)
                .map { response: MoviesResponse ->
                    response.results = response.results.filter { item ->
                        !item.adult
                    }
                    convertToModel(response)
                }
                .subscribe({ movieList ->
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(movieList)
                }, { throwable ->
                    // keep a Completable for future retry
                    setRetry(Action { loadAfter(params, callback) })
                    // publish the error
                    networkState.postValue(NetworkState.error(throwable.message))
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<MovieInfo>) {
        // ignored, since we only ever append to our initial load
    }

    override fun getKey(item: MovieInfo): Int {
        return item.page
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}