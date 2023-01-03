package com.mobiria.bnft.ui.fragment.product_details

import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.model.BidHistoryItem
import com.mobiria.bnft.utils.Constants

class BidHistoryItemViewModel(val mActivity: BaseActivity, val item: BidHistoryItem) : ViewModel {

    @JvmField
    val mBidHistoryItem = ObservableField<BidHistoryItem>()

    @JvmField
    val mBidPlacedBy = ObservableField<String>()

    @JvmField
    val mImage = ObservableField<String>()

    @JvmField
    val mDateTime = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    init {
        mBidHistoryItem.set(item)
        with(item) {
            mBidPlacedBy.set(name)
            mDateTime.set(date)
            mImage.set(image)
            mPrice.set(price)
        }
    }

    override fun close() {
    }

}