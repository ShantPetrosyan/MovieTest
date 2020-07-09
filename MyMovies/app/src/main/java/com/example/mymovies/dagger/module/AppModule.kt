package com.example.mymovies.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger2 - Module to provide objects into injectable classes
 */
@Module
class AppModule {
    // Provides a context as singleton
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application
}