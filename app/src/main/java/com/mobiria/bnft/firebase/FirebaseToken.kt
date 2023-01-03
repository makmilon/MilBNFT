package com.mobiria.bnft.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseToken {

    var token: String? = "android"

    fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result
            if (!token.isNullOrEmpty()) {
                //AppObject.fcmToken = token as String
            }
        })
    }

}