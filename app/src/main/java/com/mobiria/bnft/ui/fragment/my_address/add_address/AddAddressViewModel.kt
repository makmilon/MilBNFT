package com.mobiria.bnft.ui.fragment.my_address.add_address

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel

class AddAddressViewModel(val mActivity: BaseActivity) : ViewModel {

   @JvmField
    val mAddressText = ObservableField<String>()

    @JvmField
    val mBuildingName = ObservableField<String>()

    @JvmField
    val mLandmark = ObservableField<String>()

   @JvmField
    val addressType = ObservableField<Int>()

    init {
     addressType.set(1)
    }

    fun addressTypeHomeClick(view: View) {
        addressType.set(1)
    }

    fun addressTypeOfficeClick(view: View) {
        addressType.set(2)
    }

    override fun close() {
    }

}