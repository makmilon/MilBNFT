package com.mobiria.bnft.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.mobiria.bnft.firebase.FirebaseToken
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BNFTApplication : MultiDexApplication() {

    companion object {
        @JvmStatic
        var applicationHandler: Handler? = null
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var context: Context? = null

        private val TAG = BNFTApplication::class.java.simpleName
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FirebaseApp.initializeApp(this)
        FirebaseToken.getFCMToken()
        applicationHandler = applicationContext?.getMainLooper()?.let { Handler(it) }



        //   FirebaseApp.initializeApp(this)


      /*  // force update
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        // set in-app defaults
        val remoteConfigDefaults: MutableMap<String, Any> = HashMap()
        remoteConfigDefaults[ForceUpdateChecker.KEY_UPDATE_REQUIRED] = false
        remoteConfigDefaults[ForceUpdateChecker.KEY_CURRENT_VERSION] = "1.0"
        remoteConfigDefaults[ForceUpdateChecker.KEY_UPDATE_URL] =
            "https://play.google.com/store/apps/details?id=com.mobiria.bnft"
        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults)
        firebaseRemoteConfig.fetch(60) // fetch every minutes
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "remote config is fetched.")
                    firebaseRemoteConfig.fetchAndActivate()
                }
            }*/
    }

}