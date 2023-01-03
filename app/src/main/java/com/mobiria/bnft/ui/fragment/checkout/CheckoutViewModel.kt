package com.mobiria.bnft.ui.fragment.checkout

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_address.MyAddressFragment
import com.mobiria.bnft.ui.fragment.my_address.add_address.AddAddressFragment
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.ui_navigation.AppNavigator

class CheckoutViewModel(val mActivity: BaseActivity, val item: ODataProductDetails) : ViewModel {

    @JvmField
    val mProductDetails = ObservableField<ODataProductDetails>()

    @JvmField
    val mProductImage = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mName = ObservableField<String>()

    @JvmField
    val mSizeText = ObservableField<String>()

    @JvmField
    val mSizeId = ObservableField<String>()

    @JvmField
    val mAddress = ObservableField<String>()

    @JvmField
    val isAddressNotFound = ObservableField<Boolean>()


    init {
        isAddressNotFound.set(false)
        mProductDetails.set(item)
        with(item){
            mProductImage.set(product?.image_path)
            mPrice.set(product?.price)
            mName.set(product?.name)
        }
    }

    fun onItemClick(view: View){
      //  mActivity?.replaceFragment(DetailsFragment())
    }

    fun onAddAddressClick(view: View) {
        if (mActivity.pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            mActivity.replaceFragment(AddAddressFragment())
        } else {
            AppNavigator.navigateToLoginActivity(mActivity, false)
        }

    }

    fun onChangeAddressClick(view: View) {
        if (mActivity.pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            mActivity.replaceFragment(MyAddressFragment())
        } else {
            AppNavigator.navigateToLoginActivity(mActivity, false)
        }
    }

    override fun close() {
    }
}