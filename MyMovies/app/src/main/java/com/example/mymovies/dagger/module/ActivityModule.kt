package com.example.mymovies.dagger.module

import com.example.mymovies.view.detail.MovieDetailActivity
import com.example.mymovies.view.main.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger2 Module of injectable Activities
 * They should extends DaggerAppCompatActivity
 */
@Module
abstract class ActivityModule {
    // BW API screen
    @ContributesAndroidInjector
    internal abstract fun contributeMoviesActivity(): MoviesActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMovieDetailActivity(): MovieDetailActivity
}