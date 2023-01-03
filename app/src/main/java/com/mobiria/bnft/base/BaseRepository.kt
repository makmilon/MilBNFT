package com.mobiria.bnft.base

import android.app.ProgressDialog
import android.content.Context
import com.mobiria.bnft.utils.app_loader.CustomLoaderDialog
import com.mobiria.bnft.utils.handler.ExceptionHelper
import com.mobiria.bnft.utils.preference_data.PreferenceStore

abstract class BaseRepository {

    private var pref: PreferenceStore? = null
    private var mProgressDialog: ProgressDialog? = null

    open fun getPref(context: Context) : PreferenceStore {
        if (pref == null) {
            pref = PreferenceStore(context)
        }
        return pref!!
    }

    open fun startAnim(context: Context) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = CustomLoaderDialog.createProgressDialog(context, false)
            } else {
                mProgressDialog!!.show()
            }
        } catch (e: Exception) {
            ExceptionHelper.printStackTrace(e)
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
}