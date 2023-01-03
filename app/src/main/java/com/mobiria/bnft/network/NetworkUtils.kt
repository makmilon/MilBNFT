package com.mobiria.bnft.network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    const val API_KEY = "dummy_key"
    const val BASE_URL="https://jarsite.com/bnft/api/v1/"

    fun isNetworkConnected(activity: Activity): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}