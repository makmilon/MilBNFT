package com.mobiria.bnft.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity

class LoginViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mCallback: LoginInterface

    fun onMetamaskClick(view: View) {
        mCallback.onFailure("Only Skip is working...")
    }
    fun onWalletConnectClick(view: View) {
        mCallback.onStarted()
    }
    fun onSkipClick(view: View) {
        mCallback.onSuccess()
    }
}