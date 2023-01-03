package com.mobiria.bnft.ui.fragment.search.filter.sort_by

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.search.filter.FilterData
import com.mobiria.bnft.ui.fragment.search.filter.category.CategoryClickEvent
import com.mobiria.bnft.ui.fragment.search.filter.category.ODataCategory

class SortByViewModel(val mActivity: BaseActivity, item: ODataCategory, val mCallBack: CategoryClickEvent) : ViewModel {

    @JvmField
    val mCategory = ObservableField<ODataCategory>()

    @JvmField
    val mText = ObservableField<String>()

    @JvmField
    val isTrue = ObservableField<Int>()

    init {
        mCategory.set(item)
        with(item){
            mText.set("$name")
            isTrue.set(3)
            FilterData.sortBy = "new"
        }
    }

    fun onPriceLowToHighClick(view: View){
        isTrue.set(1)
        FilterData.sortBy = "lpf"
    }
    fun onPriceHighToLowClick(view: View){
        isTrue.set(2)
        FilterData.sortBy = "hpf"
    }
    fun onRecentlyAddedClick(view: View){
        isTrue.set(3)
        FilterData.sortBy = "new"
    }



    fun onApplyClick(view: View) {
        mActivity.onBackPressed()
    }

    override fun close() {
    }
}