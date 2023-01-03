package com.mobiria.bnft.ui.fragment.product_details.adapter

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.model.DetailsItem

class DetailItemViewModel(val mActivity: BaseActivity, val item: DetailsItem) : ViewModel {

    @JvmField
    val mSizeItem = ObservableField<DetailsItem>()

   @JvmField
   val mName = ObservableField<String>()

   @JvmField
   val mValue = ObservableField<String>()

    init {
        mSizeItem.set(item)
        with(item) {
            mName.set(name)
            mValue.set(value)
        }
    }

    override fun close() {
    }
}