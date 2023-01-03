package com.mobiria.bnft.ui.fragment.product_details

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.R
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.checkout.CheckoutFragment
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesFragment
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.myToast
import com.mobiria.bnft.utils.ui_navigation.AppNavigator

class DetailsViewModel(val mActivity: BaseActivity, val item: ODataProductDetails, val mCallBack: DialogListener) : ViewModel {

    @JvmField
    val mProductDetails = ObservableField<ODataProductDetails>()

    @JvmField
    val isTrue = ObservableField<Boolean>()

    @JvmField
    val isAuction = ObservableField<Boolean>()

    @JvmField
    val isMFTImage = ObservableField<Boolean>()

    @JvmField
    val mMFTImage = ObservableField<String>()

    @JvmField
    val mProductImage = ObservableField<String>()

    @JvmField
    val mSellerImage = ObservableField<String>()

    @JvmField
    val mSellerName = ObservableField<String>()

    @JvmField
    val mAboutCollection = ObservableField<String>()

    @JvmField
    val mMinBidPrice = ObservableField<String>()

   @JvmField
   val isAboutCollection = ObservableField<Boolean>()

   @JvmField
   val isProperties = ObservableField<Boolean>()

   @JvmField
   val isBidHistory = ObservableField<Boolean>()

   @JvmField
   val isDetails = ObservableField<Boolean>()

   @JvmField
   val isPriceHistory = ObservableField<Boolean>()

    @JvmField
    val mSizeText = ObservableField<String>()

    @JvmField
    val mSizeId = ObservableField<String>()

    @JvmField
    val mProductId = ObservableField<String>()


    init {

        mProductDetails.set(item)
        if (mProductDetails != null) {
            try {
                with(mProductDetails.get()!!) {
                    mMFTImage.set(product?.nft_image)
                    mProductImage.set(product?.image_path)
                    mSellerImage.set(product?.seller_image)
                    mSellerName.set(product?.seller_name)
                    mAboutCollection.set(product?.description)
                    mMinBidPrice.set(product?.min_bid_price)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun onClickBidNow(view: View){
        if (mActivity.pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            CustomDialog.showDialogPlaceBid(mActivity, item, mCallBack)
        }  else {
            AppNavigator.navigateToLoginActivity(mActivity, false, "product_details_auction",  mProductId.get().toString())
        }
    }

    fun onClickBuyNow(view: View){
        if (mActivity.pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            if (mSizeId.get().isNullOrEmpty()) {
                mActivity.myToast("please choose size!")
            } else {
                try {
                    val fragment = CheckoutFragment()
                    fragment.newInstance(mProductDetails.get(), mSizeText.get()!!, mSizeId.get()!!)
                    mActivity?.replaceFragment(fragment)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            AppNavigator.navigateToLoginActivity(mActivity, false, "product_details", mProductId.get().toString())
        }
    }

    fun onAboutCollectionClick(view: View) {
        if (isAboutCollection.get() == true) {
            isAboutCollection.set(false)
        } else {
            isAboutCollection.set(true)
        }
    }

    fun onNftImageClick(view: View) {
        if (isMFTImage.get() == true) {
            isMFTImage.set(false)
        } else {
            isMFTImage.set(true)
        }
    }

    fun onPropertiesClick(view: View) {
        if (isProperties.get() == true) {
            isProperties.set(false)
        } else {
            isProperties.set(true)
        }
    }

    fun onPriceHistoryClick(view: View) {
        if (isPriceHistory.get() == true) {
            isPriceHistory.set(false)
        } else {
            isPriceHistory.set(true)
        }
    }

    fun onBidHistoryClick(view: View) {
        if (isBidHistory.get() == true) {
            isBidHistory.set(false)
        } else {
            isBidHistory.set(true)
        }
    }

    fun onDetailsClick(view: View) {
        if (isDetails.get() == true) {
            isDetails.set(false)
        } else {
            isDetails.set(true)
        }
    }

    fun onSimilarCollectionClick(view: View) {
        try {
            val fragment = HoodiesFragment()
            fragment.newInstance("Similar Collection", item.product?.category_id.toString())
            mActivity.replaceFragment(fragment)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun close() {
    }
}