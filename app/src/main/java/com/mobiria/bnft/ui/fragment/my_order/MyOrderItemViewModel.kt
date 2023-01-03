package com.mobiria.bnft.ui.fragment.my_order

import android.R.attr
import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_order.order_detail.OrderDetailsFragment
import com.mobiria.bnft.utils.Constants
import android.R.attr.label

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import com.mobiria.bnft.utils.AndroidUtility


class MyOrderItemViewModel(val mActivity: BaseActivity, val item: SalesHistoryItem, isShowTime: Boolean = false) : ViewModel {

   @JvmField
   val mSalesHistoryItem = ObservableField<SalesHistoryItem>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mOrderNumber = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mOrderStatus = ObservableField<String>()

    @JvmField
    val mSize = ObservableField<String>()

    @JvmField
    val mTransactionHash = ObservableField<String>()

    @JvmField
    val mDate = ObservableField<String>()

    @JvmField
    val isTime = ObservableField<Boolean>()

    init {
        isTime.set(isShowTime)
        mSalesHistoryItem.set(item)
        with(item) {
            mImage.set(product_image)
            mOrderNumber.set(order_no)
            mPrice.set(sales_price+" "+ Constants.CURRENCY)
            mTransactionHash.set(transaction_hash)
            mDate.set(order_date)
            mOrderStatus.set(order_status)
            mSize.set("Size : $size")
        }
    }


    fun onItemClick(view: View) {
        val fragment = OrderDetailsFragment()
        fragment.newInstance(item.order_id.toString(), item.order_no.toString())
        mActivity.replaceFragment(fragment)
    }

    fun onCopyTextClick(view: View) {
        AndroidUtility.setClipboard(mActivity, mTransactionHash.get()+"")
    }

    override fun close() {
    }
}