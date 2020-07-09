package com.example.mymovies.helpers

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit


fun AppCompatActivity.showFragment(
    fragment: Fragment,
    @IdRes containerViewId: Int,
    tag: String? = null
) {
    supportFragmentManager.commit {
        replace(
            containerViewId,
            fragment,
            tag
        )
    }
}

object Navigator {
    fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
