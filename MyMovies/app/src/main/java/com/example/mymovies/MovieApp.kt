package com.example.mymovies

import com.example.mymovies.dagger.component.DaggerAppComponent
import dagger.android.DaggerApplication
import timber.log.Timber

class MovieApp : DaggerApplication() {
    private val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector() = appComponent
}