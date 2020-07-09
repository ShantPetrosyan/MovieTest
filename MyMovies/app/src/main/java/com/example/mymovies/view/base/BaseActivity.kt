package com.example.mymovies.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.mymovies.dagger.module.DaggerViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    protected inline fun <reified T : ViewModel> getViewModel(): T =
            ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}