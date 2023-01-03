package com.mobiria.bnft.ui.fragment.auctions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentAuctionBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.AuctionsItemViewModel
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.AuctionsListAdapter
import com.mobiria.bnft.ui.fragment.home.model.AuctionsItem
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class AuctionFragment : BaseFragment() {
    private lateinit var binding: FragmentAuctionBinding
    private val mAuctionsListAdapter: AuctionsListAdapter = AuctionsListAdapter(ArrayList())
    var viewModels = ArrayList<ViewModel>()

    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAuctionBinding.bind(inflater.inflate(R.layout.fragment_auction, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity.resources.getString(R.string.aucttions), false, false)

        // Auctions List setup call
        auctionsListSetup()
        onClickHandler()
        if(mActivity.isInternetAvailable()) {
            auctionListAPI()
        }
    }

    private fun onClickHandler(){
        binding.rvAuctions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) { //SCROLL_STATE_IDLE
                    Log.e("scrolling", "List size  : " +viewModels.size +"  "+recyclerView.scrollState)
                    if (viewModels.size == recyclerView.scrollState) {
                        auctionListAPI()
                    }
                }
            }
        })
    }

    // Auctions List Setup
    private fun auctionsListSetup(){
        with(binding) {
            rvAuctions.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAuctions.isNestedScrollingEnabled = false
            rvAuctions.adapter = mAuctionsListAdapter
        }
    }


    // AUCTION LIST API CALL
    private fun auctionListAPI(){
        startAnim()
        mActivity.viewModel.productListListAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, page.toString(),
            Constants.SALE_TYPE_BID, "", Constants.SORT_BY, "", "", "")
        mActivity.viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    if (!it?.oData?.result.isNullOrEmpty()){
                        if (page == 1){
                            viewModels.clear()
                        }
                        binding.emptyLayout.layoutMain.visibility = View.GONE
                        for (i in 0 until it?.oData?.result?.size!!) {
                            val model = AuctionsItem(it.oData.result.get(i).sale_end_date,
                                it.oData.result.get(i).sale_end_date_timestamp,
                                it.oData.result.get(i).image_path,
                                it.oData.result.get(i).price,
                                it.oData.result.get(i).name,
                                it.oData.result.get(i).id)
                            viewModels.add(AuctionsItemViewModel(mActivity, model, false))
                        }
                        if (page == 1) {
                            mAuctionsListAdapter.setList(viewModels)
                        } else {
                            mAuctionsListAdapter.addList(viewModels)
                        }
                        page = page+1
                    } else {
                        if (page == 1) {
                            binding.emptyLayout.layoutMain.visibility = View.VISIBLE
                        }
                    }
                }
            } else {
                mActivity.myToast(it?.message!!)
            }
        })
    }

}