package com.mobiria.bnft.utils.animation

import android.view.View
import android.view.animation.AnimationUtils
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity

class CallAnimation {
    companion object {
        // FADE IN ANIMATION
        fun fadeInView(mActivity: BaseActivity, view: View){
            val animation = AnimationUtils.loadAnimation(mActivity, R.anim.fadein)
            //starting the animation
            view.startAnimation(animation)
        }
    }
}