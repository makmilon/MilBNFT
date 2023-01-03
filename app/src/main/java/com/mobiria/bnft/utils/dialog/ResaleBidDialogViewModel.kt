package com.mobiria.bnft.utils.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.fragment.my_order.order_detail.ODataOrderDetail
import com.mobiria.bnft.ui.fragment.my_order.resale_interface.ResaleListener
import com.mobiria.bnft.utils.myToast
import java.util.*

class ResaleBidDialogViewModel(val mActivity: BaseActivity, val  item: ODataOrderDetail,
                               val mDialog: Dialog, val listener: ResaleListener?) : ViewModel(), DatePickerDialog.OnDateSetListener {

    @JvmField
    val mImage = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mResalePrice = ObservableField<String>()

    @JvmField
    val mEndDate = ObservableField<String>()

    @JvmField
    val mTransactionHash = ObservableField<String>()

    init {
        with(item) {
            mImage.set(nft_image)
            mPrice.set(price)
            mTransactionHash.set(transaction_hash)
        }
    }


    fun onEndDateClick(view: View) {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(mActivity, this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    fun onProceedClick(view: View) {
        try {
            if (mResalePrice.get().isNullOrEmpty()) {
                mActivity.myToast("Please Enter Price...")
            } else if (mEndDate.get().isNullOrEmpty()) {
                mActivity.myToast("Please Select End Date...")
            } else {
                mDialog.dismiss()
                listener?.setValue(item.product_id.toString(), mResalePrice.get(), mEndDate.get())
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        Log.e("end_date", "Selected Date : ---- "+p1+":"+p2+":"+p3)
        mEndDate.set("$p1-"+p2.plus(1)+"-$p3")
    }

}