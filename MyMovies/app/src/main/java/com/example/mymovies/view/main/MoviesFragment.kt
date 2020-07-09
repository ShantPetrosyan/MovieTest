package com.example.mymovies.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovies.R
import com.example.mymovies.data.NetworkState
import com.example.mymovies.data.Status
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.view.base.BaseFragment
import com.example.mymovies.view.detail.MovieDetailActivity
import com.example.mymovies.view.main.adapter.MoviesAdapter
import com.example.mymovies.viewmodel.main.MoviesListViewModel
import kotlinx.android.synthetic.main.item_network_state.*
import kotlinx.android.synthetic.main.movies_fragment.*

class MoviesFragment : BaseFragment() {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()

        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        rvMovies.apply {
            moviesAdapter = MoviesAdapter(
                retryCallback = {
                    viewModel.retry()
                },
                itemClickedCallBack = {
                    showMovieDetailPage(it)
                })
            adapter = moviesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        viewModel.movieList.observe(viewLifecycleOwner, Observer { moviesAdapter.submitList(it) })
        viewModel.getNetworkState()
            .observe(viewLifecycleOwner, Observer { moviesAdapter.setNetworkState(it) })
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        viewModel.getRefreshState().observe(viewLifecycleOwner, Observer { networkState ->
            if (moviesAdapter.currentList != null) {
                if (moviesAdapter.currentList!!.size > 0) {
                    usersSwipeRefreshLayout.isRefreshing =
                        networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        })
        usersSwipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility =
            if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }

    private fun showMovieDetailPage(item: MovieInfo) {
        (activity as MoviesActivity).let {
            val intent = MovieDetailActivity.newInstance(it, item)
            it.startActivity(intent)
        }
    }
}
