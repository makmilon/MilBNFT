package com.mobiria.bnft.ui.fragment.product_details.properties

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.ViewModel

class PropertiesAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[PropertiesItemViewModel::class.java] = R.layout.layout_properties_item
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