package com.mobiria.bnft.ui.dashboard.bottom_nemu

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity

class BottomMenuViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mBottomMenuInterface: BottomMenuInterface

    @JvmField
    val mProfileImage = ObservableField<String>()

    fun onHomeClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("home")
    }
    fun onAuctionsClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("auction")
    }
    fun onSearchClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("search")
    }
    fun onNotificationClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("notification")
    }
    fun onAccountClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("account")
    }
}