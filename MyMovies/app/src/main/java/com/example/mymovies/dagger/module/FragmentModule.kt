package com.example.mymovies.dagger.module

import com.example.mymovies.view.main.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    // BW API screen
    @ContributesAndroidInjector
    internal abstract fun contributeMoviesFragment(): MoviesFragment
}