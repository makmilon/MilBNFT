package com.mobiria.bnft.utils.dialog

import android.app.Dialog
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails
import com.mobiria.bnft.utils.DateFormatChanger
import com.mobiria.bnft.utils.manage_timer.ManageTimer
import com.mobiria.bnft.utils.myToast


class PlaceBidViewModel(val mActivity: BaseActivity, val  item: ODataProductDetails,
                        val mDialog: Dialog, val listener: DialogListener?) : ViewModel() {

    @JvmField
    val mImage = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mResalePrice = ObservableField<String>()

    @JvmField
    val mEndDate = ObservableField<String>()

    @JvmField
    val mDateTime = ObservableField<String>()

    @JvmField
    val mName = ObservableField<String>()

    init {
        with(item) {
            mPrice.set(product?.price)
            mName.set(product?.name)
            if (!product?.sale_end_date.isNullOrEmpty()) {
                val dateTime = DateFormatChanger.convertDateTime(product?.sale_end_date)
                mEndDate.set("Sale ends  $dateTime")
            }

            if (!product?.sale_end_date_timestamp.isNullOrEmpty()) {
                val currentTimestamp = System.currentTimeMillis()/1000
                // Log.e("auction_time", "$currentTimestamp     -   $sale_end_date_timestamp")
                if (product?.sale_end_date_timestamp!!.toLong() > currentTimestamp) {
                    val timeStamp = product?.sale_end_date_timestamp!!.toLong()-currentTimestamp
                    ManageTimer.callTimer(timeStamp, mDateTime)
                } else {
                    mDateTime.set("Time End")
                }
            }
        }
    }



    fun onProceedClick(view: View) {
        try {
            if (mResalePrice.get().isNullOrEmpty()) {
                mActivity.myToast("Please Enter Price...")
            } else {
                mDialog.dismiss()
                listener?.setValue(mResalePrice.get().toString(), 1)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}