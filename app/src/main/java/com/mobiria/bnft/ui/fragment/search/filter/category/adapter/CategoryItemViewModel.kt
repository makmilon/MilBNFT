package com.mobiria.bnft.ui.fragment.search.filter.category.adapter

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.search.filter.category.CategoryClickEvent
import com.mobiria.bnft.ui.fragment.search.filter.category.ODataCategory

class CategoryItemViewModel(val mActivity: BaseActivity, item: ODataCategory, val mCallBack: CategoryClickEvent) : ViewModel {

    @JvmField
    val mCategory = ObservableField<ODataCategory>()

    @JvmField
    val mText = ObservableField<String>()

    @JvmField
    val mIsDefault = ObservableField<Boolean>()

    init {
        mCategory.set(item)
        with(item){
            mText.set("$name")
            mIsDefault.set(selected)
        }
    }

    fun onDefaultClick(view: View) {
        mCallBack.onCategoryClick("default", mCategory.get())
    }

    override fun close() {
    }
}