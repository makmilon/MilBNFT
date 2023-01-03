package com.mobiria.bnft.ui.fragment.product_details.adapter

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.ItemCallBack
import com.mobiria.bnft.ui.fragment.product_details.model.SizeItem

class SizeItemViewModel(val mActivity: BaseActivity, val item: SizeItem, val mCallBack: ItemCallBack) : ViewModel {

    @JvmField
    val mSizeItem = ObservableField<SizeItem>()

    @JvmField
    val isTrueItem = ObservableField<Boolean>()

   @JvmField
   val mTextSize = ObservableField<String>()

    init {
        mSizeItem.set(item)
        with(item) {
            mTextSize.set(text)
            isTrueItem.set(isTrue)
        }
    }

    fun onItemClick(view: View){
        mCallBack.itemRefresh(item)
    }

    override fun close() {
    }
}