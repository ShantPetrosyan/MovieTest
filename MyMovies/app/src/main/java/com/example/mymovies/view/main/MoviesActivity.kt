package com.example.mymovies.view.main

import android.os.Bundle
import android.widget.Toast
import com.example.mymovies.R
import com.example.mymovies.helpers.Navigator
import com.example.mymovies.helpers.showFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MoviesActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setTitle(R.string.movie_list)
        showFragment(
            if (savedInstanceState == null
                || supportFragmentManager.findFragmentByTag(TAG_MOVIE_LIST) == null
            ) {
                MoviesFragment.newInstance()
            } else {
                supportFragmentManager.findFragmentByTag(TAG_MOVIE_LIST)!!
            },
            R.id.fragment_container_view,
            TAG_MOVIE_LIST
        )

        if (!Navigator.verifyAvailableNetwork(this)) {
            Toast.makeText(this, getString(R.string.connection_issue), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG_MOVIE_LIST = "MOVIE_LIST"
    }
}
