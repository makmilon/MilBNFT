package com.mobiria.bnft.ui.fragment.home.hud_and_auctions

import android.annotation.SuppressLint
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.comman.DataBindingRecyclerViewAdapter
import com.mobiria.bnft.ui.comman.EmptyItemViewModel
import com.mobiria.bnft.ui.comman.ViewModel

class AuctionsListAdapter(viewModels: MutableList<ViewModel>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[AuctionsItemViewModel::class.java] = R.layout.layout_auction_item
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