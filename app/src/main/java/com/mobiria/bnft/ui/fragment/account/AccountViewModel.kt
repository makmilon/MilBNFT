package com.mobiria.bnft.ui.fragment.account

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.account.edit_profile.EditProfileFragment
import com.mobiria.bnft.ui.fragment.auctions.my_auction.MyAuctionFragment
import com.mobiria.bnft.ui.fragment.favorite.FavoriteFragment
import com.mobiria.bnft.ui.fragment.my_address.MyAddressFragment
import com.mobiria.bnft.ui.fragment.my_order.MyOrderFragment
import com.mobiria.bnft.utils.dialog.CustomDialog

class AccountViewModel(val mActivity: BaseActivity, val mCallBack: ListSelector) : ViewModel {

    @JvmField
    val mImage = ObservableField<String>()

    @JvmField
    val mName = ObservableField<String>()

    @JvmField
    val mEmail = ObservableField<String>()

    init {
        try {
            mName.set(mActivity.userLogin?.display_name)
            mImage.set(mActivity.userLogin?.user_image)
            mEmail.set(mActivity.userLogin?.email)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun onEditProfileClick(view: View) {
        mActivity.replaceFragment(EditProfileFragment())
    }
    fun onMyOrdersClick(view: View) {
        mActivity.replaceFragment(MyOrderFragment())
    }
    fun onMyAuctionClick(view: View) {
        mActivity.replaceFragment(MyAuctionFragment())
    }
    fun onMyAddressClick(view: View) {
        mActivity.replaceFragment(MyAddressFragment())
    }
    fun onFavoritesClick(view: View) {
        mActivity.replaceFragment(FavoriteFragment())
    }
    fun onLogoutClick(view: View) {
        CustomDialog.showLogoutDialog(mActivity, "",object: DialogListener{
            override fun setValue(value: String, position: Int) {
                mCallBack.selectedList(value, position)
            }
        })
    }

    override fun close() {
    }
}