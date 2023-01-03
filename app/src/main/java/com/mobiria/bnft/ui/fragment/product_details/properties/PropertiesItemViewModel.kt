package com.mobiria.bnft.ui.fragment.product_details.properties

import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.model.PropertiesItem

class PropertiesItemViewModel(val mActivity: BaseActivity, val item: PropertiesItem) : ViewModel {

    @JvmField
    val mSizeItem = ObservableField<PropertiesItem>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mValue = ObservableField<String>()

    init {
        mSizeItem.set(item)
        with(item) {
            mTitle.set(title)
            mValue.set(description)
        }
    }
    override fun close() {
    }
}