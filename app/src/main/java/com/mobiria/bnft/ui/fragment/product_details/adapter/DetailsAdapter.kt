package com.mobiria.bnft.ui.fragment.product_details.adapter

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.ViewModel

class DetailsAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[DetailItemViewModel::class.java] = R.layout.layout_product_detail_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}