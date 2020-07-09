package com.example.mymovies.dagger.module

import android.content.Context
import com.example.mymovies.data.net.CommonCloudDataStore
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CustomizationModule {
    @Provides
    @Singleton
    fun provideRetrofit(context: Context): Retrofit {
        return CommonCloudDataStore(context).buildRetrofit()
    }
}