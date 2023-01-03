package com.mobiria.bnft.ui.fragment.my_address

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_address.add_address.AddAddressFragment

class MyAddressViewModel(val mActivity: BaseActivity) : ViewModel {

   @JvmField
    val mAddressTitle = ObservableField<String>()

   @JvmField
    val mAddress = ObservableField<String>()

    init {

    }

    fun addNewAddressClickEvent(view: View){
        mActivity?.replaceFragment(AddAddressFragment())
    }

    fun editCurrentAddress(view: View) {
        mActivity?.replaceFragment(AddAddressFragment())
    }

    override fun close() {
    }

}