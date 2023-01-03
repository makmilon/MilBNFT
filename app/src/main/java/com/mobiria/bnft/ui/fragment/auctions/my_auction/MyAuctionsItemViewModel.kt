package com.mobiria.bnft.ui.fragment.auctions.my_auction

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment
import com.mobiria.bnft.utils.Constants

class MyAuctionsItemViewModel(val mActivity: BaseActivity, val item: MyAuctionItem) : ViewModel {

   @JvmField
   val mMyAuctionItem = ObservableField<MyAuctionItem>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    @JvmField
    val mEndDateTime = ObservableField<String>()

    init {
        mMyAuctionItem.set(item)
        with(item) {
            mTitle.set(name)
            mPrice.set(price+" "+Constants.CURRENCY)
            mImage.set(image)
            mEndDateTime.set(sale_end_date)
        }
    }


    fun onItemClick(view: View) {
        val fragment = DetailsFragment(true)
        fragment.newInstance(item.id.toString())
        mActivity.replaceFragment(fragment)
    }

    override fun close() {
    }
}