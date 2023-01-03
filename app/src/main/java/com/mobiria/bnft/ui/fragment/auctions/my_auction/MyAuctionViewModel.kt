package com.mobiria.bnft.ui.fragment.auctions.my_auction

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel

class MyAuctionViewModel(val mActivity: BaseActivity, val mCallBack: ListSelector) : ViewModel {

 @JvmField
 val isTabs = ObservableField<Int>()

 init {
  isTabs.set(1)
 }

 // Pending Click Event
 fun onPendingClick(view: View) {
  isTabs.set(1)
  mCallBack.selectedList("pending", 1)
 }

 // Live Click Event
 fun onLiveClick(view: View) {
  isTabs.set(2)
  mCallBack.selectedList("live", 2)
 }

 // Sold Out Click Event
 fun onSoldOurClick(view: View) {
  isTabs.set(3)
  mCallBack.selectedList("sold_out", 3)
 }

 override fun close() {
 }
}