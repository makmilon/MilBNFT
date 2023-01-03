package com.mobiria.bnft.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.network.NetworkUtils
import com.mobiria.bnft.utils.Constants

class FirebaseUserHandler {

    companion object {
         private var mFirebaseDatabase: DatabaseReference? = null
        private var mFirebaseInstance: FirebaseDatabase? = null
        private var mActivity: BaseActivity? = null
        var token: String? = ""

        fun initializeFirebase(mActivity: BaseActivity) {
            this.mActivity = mActivity
            FirebaseApp.initializeApp(mActivity)
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance?.getReference("Notifications")

            if (NetworkUtils?.isNetworkConnected(mActivity!!)!!) {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(false)
            } else {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(true)
            }
        }
        fun setFCMToken(activity: BaseActivity) {
            mActivity = activity
            initializeFirebase(mActivity!!)
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                token = task.result
                Log.d("awais","fffffff"+ token)
                if (!token.isNullOrEmpty())
                    mActivity?.pref?.saveStringData(Constants.FCM_TOKEN, token)
            })
        }

        fun deleteAllNotification(activity: BaseActivity, keyvalue: String) {
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance?.getReference("Notifications")
            if (NetworkUtils?.isNetworkConnected(activity)!!) {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(false)
            } else {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(true)
            }
            //Remove value from child
            mFirebaseDatabase!!.child(keyvalue).removeValue()
        }

        fun deleteSingleNotification(activity: BaseActivity, keyvalue: String, messageId: String) {
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance?.getReference("Notifications")
            if (NetworkUtils?.isNetworkConnected(activity)!!) {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(false)
            } else {
                val scoresRef = FirebaseDatabase.getInstance().getReference("Notifications")
                scoresRef.keepSynced(true)
            }
            //Remove value from child
            mFirebaseDatabase!!.child(keyvalue).child(messageId).removeValue()
        }
    }
}