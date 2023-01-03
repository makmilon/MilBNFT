package com.mobiria.bnft.ui.fragment.my_order.order_detail.adapter

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.EmptyItemViewModel
import com.mobiria.bnft.ui.comman.ViewModel

class OrderDetailListAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[OrderDetailsItemViewModel::class.java] = R.layout.layout_order_details_item
        mViewModelMap[EmptyItemViewModel::class.java] = R.layout.empty_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}