package com.mobiria.bnft.ui.fragment.auctions.my_auction

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.EmptyItemViewModel
import com.mobiria.bnft.ui.comman.ViewModel

class MyAuctionsListAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[MyAuctionsItemViewModel::class.java] = R.layout.layout_my_auction_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<ViewModel>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}