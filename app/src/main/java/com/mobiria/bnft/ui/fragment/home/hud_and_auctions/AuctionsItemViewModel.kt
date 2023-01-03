package com.mobiria.bnft.ui.fragment.home.hud_and_auctions

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.home.model.AuctionsItem
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment
import com.mobiria.bnft.utils.manage_timer.ManageTimer

class AuctionsItemViewModel(val mActivity: BaseActivity, val mAuction: AuctionsItem, isShowTime: Boolean = false) : ViewModel {

   @JvmField
   val mAuctionsItem = ObservableField<AuctionsItem>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mTime = ObservableField<String>()

    @JvmField
    val isTime = ObservableField<Boolean>()

    init {
        isTime.set(isShowTime)
        mAuctionsItem.set(mAuction)
        with(mAuction) {
            mTitle.set(name)
            mImage.set(image_path)
            mPrice.set(price)

            // Run timer
            if (!sale_end_date_timestamp.isNullOrEmpty()) {
                val currentTimestamp = System.currentTimeMillis()/1000
               // Log.e("auction_time", "$currentTimestamp     -   $sale_end_date_timestamp")
                if (sale_end_date_timestamp.toLong() > currentTimestamp) {
                    val timeStamp = sale_end_date_timestamp.toLong()-currentTimestamp
                    ManageTimer.callTimer(timeStamp, mTime)
                } else {
                    mTime.set("Time End")
                }
            }
        }
    }

    fun onItemClick(view: View) {
        val fragment = DetailsFragment(true, true)
        fragment.newInstance(mAuction.id.toString())
        mActivity.replaceFragment(fragment)
    }

    override fun close() {
    }
}