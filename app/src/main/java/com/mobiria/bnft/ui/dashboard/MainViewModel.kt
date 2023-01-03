package com.mobiria.bnft.ui.dashboard

import android.view.View
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.dashboard.bottom_nemu.BottomMenuInterface

class MainViewModel(val mActivity: BaseActivity) : ViewModel() {

    lateinit var mBottomMenuInterface: BottomMenuInterface

    fun onSearchClick(view: View) {
        mBottomMenuInterface.selectedBottomMenuItem("search")
    }
}