package com.example.mymovies.dagger.component

import android.app.Application
import com.example.mymovies.MovieApp
import com.example.mymovies.dagger.module.*
import com.example.mymovies.view.detail.MovieDetailActivity
import com.example.mymovies.view.main.MoviesActivity
import com.example.mymovies.view.main.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        CustomizationModule::class
    ]
)
/**
 * AppComponent is build with its modules, we have a graph with all provided instances in our graph
 */
interface AppComponent : AndroidInjector<MovieApp> {

    fun inject(moviesMainActivity: MoviesActivity)

    fun inject(movieDetailActivity: MovieDetailActivity)

    fun inject(moviesFragment: MoviesFragment)

    /**
     * Builder for AppComponent and this builder will help have an access of Application instance
     * in hall injected environments
     */
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}