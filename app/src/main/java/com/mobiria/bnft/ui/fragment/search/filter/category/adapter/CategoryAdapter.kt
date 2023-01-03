package com.mobiria.bnft.ui.fragment.search.filter.category.adapter

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.ViewModel

class CategoryAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[CategoryItemViewModel::class.java] = R.layout.filter_category_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    fun getList(): MutableList<ViewModel> {
        return mViewModels
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}