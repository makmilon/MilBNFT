package com.mobiria.bnft.ui.fragment.hoodies

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment

class HoodiesItemViewModel(val mActivity: BaseActivity, isShowTime: Boolean = false, item: ProductItem) : ViewModel {

   @JvmField
   val mProductDetails = ObservableField<ProductItem>()

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
        mProductDetails.set(item)
        with(item) {
            mTitle.set(name)
            mPrice.set(price)
            mTime.set(sale_end_date)
            mImage.set(nft_image)
        }
    }


    fun onItemClick(view: View) {
        var fragment = DetailsFragment()
        fragment.newInstance(mProductDetails.get()?.id.toString())
        mActivity.replaceFragment(fragment)
    }

    override fun close() {
    }
}