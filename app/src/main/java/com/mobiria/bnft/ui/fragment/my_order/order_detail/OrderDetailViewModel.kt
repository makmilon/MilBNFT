package com.mobiria.bnft.ui.fragment.my_order.order_detail

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_order.resale_interface.ResaleListener
import com.mobiria.bnft.utils.dialog.CustomDialog

class OrderDetailViewModel(val mActivity: BaseActivity, val item: ODataOrderDetail, val listener: ResaleListener) : ViewModel {

   @JvmField
   val mOrderDetail = ObservableField<ODataOrderDetail>()

   @JvmField
   val mImage = ObservableField<String>()

   @JvmField
   val mTitle = ObservableField<String>()

   @JvmField
   val mPrice = ObservableField<String>()

   @JvmField
   val mSize = ObservableField<String>()

   @JvmField
   val mTransactionHash = ObservableField<String>()

   @JvmField
   val mStatus = ObservableField<String>()

   @JvmField
   val isStatus = ObservableField<Boolean>()

   @JvmField
   val mAddress = ObservableField<String>()

    @JvmField
    val mSubTotal = ObservableField<String>()

    @JvmField
    val mCreatorFee = ObservableField<String>()

    @JvmField
    val mGrandTotal = ObservableField<String>()

   @JvmField
   val mTime = ObservableField<String>()

   init {
      mOrderDetail.set(item)
      with(item){
          mImage.set(product_image)
          mPrice.set(price)
          mTransactionHash.set(transaction_hash)
          mAddress.set(delivery_address?.building_name+", "+delivery_address?.street_address+" "+delivery_address?.landmark)
          mSubTotal.set(subtotal)
          mCreatorFee.set(creator_fee)
          mGrandTotal.set(grand_total)
          mSize.set("Size : $size")
          mStatus.set(order_status)
          if (order_status.equals("Delivered")) {
              if(is_resell_requested == 0) {
                  isStatus.set(true)
              }
          }
      }
   }

   fun onResaleClick(view: View) {
       CustomDialog.showResaleDialog(mActivity, item, listener)
   }
   fun onItemClick(view: View) {

    }

     override fun close() {
     }
}