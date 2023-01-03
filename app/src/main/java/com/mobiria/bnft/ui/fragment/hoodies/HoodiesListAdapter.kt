package com.mobiria.bnft.ui.fragment.hoodies

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.EmptyItemViewModel
import com.mobiria.bnft.ui.comman.ViewModel

class HoodiesListAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[HoodiesItemViewModel::class.java] = R.layout.layout_hoodies_item
        mViewModelMap[EmptyItemViewModel::class.java] = R.layout.empty_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(itemList: ArrayList<ViewModel>) {
        mViewModels?.addAll(itemList)
        notifyDataSetChanged()
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}