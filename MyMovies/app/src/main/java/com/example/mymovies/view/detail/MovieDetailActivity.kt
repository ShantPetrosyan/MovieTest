package com.example.mymovies.view.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mymovies.R
import com.example.mymovies.data.Status
import com.example.mymovies.data.model.moviedetail.MovieDetailInfo
import com.example.mymovies.data.model.movielist.MovieInfo
import com.example.mymovies.data.net.ApiConstants
import com.example.mymovies.helpers.Navigator
import com.example.mymovies.view.base.BaseActivity
import com.example.mymovies.view.main.MoviesActivity
import com.example.mymovies.viewmodel.detail.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*

class MovieDetailActivity : BaseActivity() {

    private var movieId: Int = 0
    private var movieTitle: String = ""
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbar)
        viewModel = getViewModel()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getExtraValues()
        setupData()
        viewModel.getMovieDetail(movieId)

        if (!Navigator.verifyAvailableNetwork(this)) {
            Toast.makeText(this, getString(R.string.connection_issue), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupData() {
        viewModel.movieList.observe(this, Observer {
            it?.let {
                showMovieInfo(it)
            }
        })
        viewModel.networkState
            .observe(this, Observer {
                when (it.status) {
                    Status.FAILED -> {
                        Snackbar.make(
                            root,
                            getString(R.string.error_for_request),
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                        infoView.visibility = View.INVISIBLE
                        pbLoading.visibility = View.GONE
                    }
                    Status.RUNNING -> {
                        infoView.visibility = View.INVISIBLE
                        pbLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        infoView.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE
                    }
                }
            })
    }

    private fun showMovieInfo(movieInfo: MovieDetailInfo) {
        Glide.with(this)
            .load(ApiConstants.IMAGE_HOST + "/" + ApiConstants.IMAGE_SIZE_ORIGINAL + movieInfo.backdropPath)
            .centerCrop()
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    app_bar.background = resource
                }
            })

        Glide.with(this)
            .load(ApiConstants.IMAGE_HOST + "/" + ApiConstants.IMAGE_SIZE_SMALL + movieInfo.posterPath)
            .into(imgMovie)
        txtTitle.text = getString(R.string.title, movieInfo.originalTitle)
        txtYear.text = getString(R.string.year, movieInfo.releaseDate)
        txtRate.text = getString(R.string.rate, movieInfo.voteAverage.toString())
        txtLanguages.text = getString(R.string.languages, movieInfo.spokenLanguages)
        txtProductionCountries.text =
            getString(R.string.production_countries, movieInfo.productionCountries)
        txtProductionCompanies.text =
            getString(R.string.production_companies, movieInfo.productionCompanies)
        txtBudget.text = getString(R.string.budget, movieInfo.budget.toString())
        txtOverView.text = getString(R.string.overview, movieInfo.overview)

        fab.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(movieInfo.homepage))
            startActivity(browserIntent)
        }
    }

    private fun getExtraValues() {
        intent.extras?.apply {
            if (containsKey(TAG_ID) && containsKey(TAG_TITLE)) {
                movieId = getInt(TAG_ID)
                movieTitle = getString(TAG_TITLE, "")
                toolbar_layout.title = movieTitle
            }
        }
    }

    companion object {
        private const val TAG_ID = "movie_id"
        private const val TAG_TITLE = "movie_title"

        fun newInstance(activity: MoviesActivity, movieInfo: MovieInfo) =
            Intent(activity, MovieDetailActivity::class.java).apply {
                putExtra(TAG_ID, movieInfo.id)
                putExtra(TAG_TITLE, movieInfo.title)
            }
    }
}