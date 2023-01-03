package com.mobiria.bnft.ui.fragment.home.hud_and_auctions

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.home.model.CategoriesItem
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesFragment

class HoodCattegoryItemViewModel(val mActivity: BaseActivity, val categoryItem: CategoriesItem) : ViewModel {

   @JvmField
   val mCategory = ObservableField<CategoriesItem>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mTotalCount = ObservableField<String>()

    init {
        mCategory.set(categoryItem)
        with(categoryItem){
            mTitle.set(name)
            mImage.set(image)
            mTotalCount.set(product_count.toString())
        }
    }

    fun onItemClick(view: View) {
        try {
            val fragment = HoodiesFragment()
            fragment.newInstance(mCategory.get()?.name!!, mCategory.get()?.category_id.toString())
            mActivity.replaceFragment(fragment)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun close() {
    }
}