package com.mobiria.bnft.utils.preference_data

import android.content.Context
import android.content.SharedPreferences
import com.mobiria.bnft.app.BNFTApplication


class PreferenceStore(context: Context?) {
    private var editor: SharedPreferences.Editor? = null
    private var preferences: SharedPreferences? = null

    fun saveStringData(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun saveBooleanData(key: String?, value: Boolean?) {
        editor!!.putBoolean(key, value!!)
        editor!!.apply()
    }

    fun saveIntegerData(key: String?, value: Long) {
        editor!!.putLong(key, value)
        editor!!.apply()
    }

    fun getStringData(key: String?): String? {
        return if (preferences != null) {
            preferences?.getString(key, "")
        } else {
            null
        }
    }

    fun getBooleanData(key: String?): Boolean? {
        return if (preferences != null) {
            preferences?.getBoolean(key, false)
        } else {
            false
        }
    }

    fun getIntegerData(key: String?): Long? {
        return if (preferences != null) {
            preferences?.getLong(key, 0)
        } else {
            0
        }
    }

    fun clearPrefrenceData() {
        preferences!!.edit().clear().apply()
    }

    companion object {
        @get:Synchronized
        var instance: PreferenceStore? = null
            get() {
                field =
                    if (field == null) PreferenceStore(BNFTApplication.context) else field
                return field
            }
            private set

        @Synchronized
        fun getInstance(context: Context?): PreferenceStore {
            return PreferenceStore(context)
        }
    }

    init {
        if (context != null) {
            preferences = context.getSharedPreferences(
                "TALENTBAZAR", Context.MODE_PRIVATE
            )
            editor = preferences?.edit()
        }
    }
}