package com.mobiria.bnft.ui.splash

import android.view.View
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.utils.animation.CallAnimation

class SplashViewModel(val mActivity: BaseActivity) : ViewModel() {
    fun callAnimation(view: View) {
        CallAnimation.fadeInView(mActivity, view)
    }
}