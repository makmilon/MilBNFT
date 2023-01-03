package com.mobiria.bnft.ui.fragment.my_order.order_detail.adapter

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.utils.dialog.CustomDialog

class OrderDetailsItemViewModel(val mActivity: BaseActivity) : ViewModel {

 @JvmField
 val mImage = ObservableField<String>()

 @JvmField
 val mTitle = ObservableField<String>()

 @JvmField
 val mPrice = ObservableField<String>()

 @JvmField
 val mTime = ObservableField<String>()


 fun onResaleClick(view: View) {
  /*CustomDialog.showResaleDialog(mActivity,  object : DialogListener {
   override fun setValue(value: String, position: Int) {

   }
  })*/
 }
 fun onItemClick(view: View) {

 }

 override fun close() {
 }
}