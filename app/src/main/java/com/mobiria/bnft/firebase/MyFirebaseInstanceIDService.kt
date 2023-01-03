package com.mobiria.bnft.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.mobiria.bnft.utils.preference_data.PreferenceStore

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    lateinit var pref: PreferenceStore
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("fcm_token_class", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.e("mathew", token)
        })
    }


    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.
    }

    companion object {
        private const val TAG = "MyFirebaseIIDService"
    }
}