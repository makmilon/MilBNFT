package com.mobiria.bnft.utils.dialog

import android.app.Dialog
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_order.order_detail.ODataOrderDetail
import com.mobiria.bnft.ui.fragment.my_order.resale_interface.ResaleListener

class ResaleDialogViewModel(val mActivity: BaseActivity, val mDialog: Dialog, val item: ODataOrderDetail,val listener: ResaleListener?) : ViewModel {

    @JvmField
    val mDetails = ObservableField<ODataOrderDetail>()


    @JvmField
    val mProductImage = ObservableField<String>()

    @JvmField
    val isMFTImage = ObservableField<Boolean>()

    @JvmField
    val mMFTImage = ObservableField<String>()

    @JvmField
    val mTransaction = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mAboutCollection = ObservableField<String>()

    @JvmField
    val isDetails = ObservableField<Boolean>()


    init {
        mDetails.set(item)
        with(item){
            mProductImage.set(product_image)
            mMFTImage.set(nft_image)
            mPrice.set(price)
            mTransaction.set(transaction_hash)
        }
        mAboutCollection.set(mActivity.resources.getString(R.string.dummy_text))
    }


    fun onResaleClick(view: View) {
        try {
            mDialog.dismiss()
            CustomDialog.showResaleBidDialog(mActivity, item, listener)
        } catch (e: Exception){
            e.printStackTrace()
        }

    }

    fun onNftImageClick(view: View) {
        if (isMFTImage.get() == true) {
            isMFTImage.set(false)
        } else {
            isMFTImage.set(true)
        }
    }

    fun onDetailsClick(view: View) {
        if (isDetails.get() == true) {
            isDetails.set(false)
        } else {
            isDetails.set(true)
        }
    }


    override fun close() {
    }
}