package com.mobiria.bnft.ui.fragment.home.dropped

import android.view.View
import androidx.databinding.ObservableField
import com.google.firebase.messaging.Constants
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.home.model.PopularCollectionsItem
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment

class DroppedItemViewModel(val mActivity: BaseActivity, mCollection: PopularCollectionsItem, val fragment: DetailsFragment? = null) : ViewModel {

   @JvmField
   val mPopularCollectionsItem = ObservableField<PopularCollectionsItem>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    init {
        mPopularCollectionsItem.set(mCollection)
        with(mCollection) {
            mTitle.set(name)
            mPrice.set("$price ETH")
            mImage.set(image_path)
        }
    }

    fun onItemClick(view: View) {
        if (fragment != null){
            try {
                fragment.productId = mPopularCollectionsItem.get()?.id.toString()
                fragment.productDetailsAPI(mPopularCollectionsItem.get()?.id.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            var fragment = DetailsFragment()
            fragment.newInstance(mPopularCollectionsItem.get()?.id.toString())
            mActivity.replaceFragment(fragment)
        }
    }

    override fun close() {
    }
}