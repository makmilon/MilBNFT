package com.mobiria.bnft.ui.fragment.my_address

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_address.interfaces.AddressClickEvent
import com.mobiria.bnft.utils.dialog.CustomDialog

class AddressItemViewModel(val mActivity: BaseActivity, item: ODataAddressItem, val mCallBack: AddressClickEvent) : ViewModel {

    @JvmField
    val mAddressItem = ObservableField<ODataAddressItem>()

    @JvmField
    val mBuildingName = ObservableField<String>()

    @JvmField
    val mIsDefault = ObservableField<Int>()

    @JvmField
    val mAddress = ObservableField<String>()

    init {
        mAddressItem.set(item)
        with(item){
            mBuildingName.set("$building_name")
            mAddress.set("$street_address")
            mIsDefault.set(is_default)
        }
    }

    fun onDeleteAddressClick(view: View) {
        CustomDialog.showLogoutDialog(mActivity,mActivity?.getString(R.string.sure_delete_notif), object: DialogListener {
            override fun setValue(value: String, position: Int) {
                mCallBack.onAddressClick("delete", mAddressItem.get())
            }
        })

    }

    fun onEditAddressClick(view: View) {
        mCallBack.onAddressClick("edit", mAddressItem.get())
    }

    fun onDefaultAddressClick(view: View) {
        mCallBack.onAddressClick("default", mAddressItem.get())
    }

    override fun close() {
    }
}