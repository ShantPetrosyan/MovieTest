package com.example.mymovies.dagger.module

import android.app.Application
import com.example.mymovies.data.repository.MovieDetailRepository
import com.example.mymovies.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RepositoryModule {
    @Provides
    fun provideMoviesRepository(app: Application, retrofit: Retrofit): MoviesRepository {
        return MoviesRepository(app, retrofit)
    }

    @Provides
    fun provideMovieDetailRepository(app: Application, retrofit: Retrofit): MovieDetailRepository {
        return MovieDetailRepository(app, retrofit)
    }
}