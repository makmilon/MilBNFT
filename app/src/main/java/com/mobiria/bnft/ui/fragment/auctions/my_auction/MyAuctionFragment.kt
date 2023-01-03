package com.mobiria.bnft.ui.fragment.auctions.my_auction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentMyAuctionBinding
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class MyAuctionFragment : BaseFragment(), ListSelector {
    private lateinit var binding: FragmentMyAuctionBinding
    private val mMyAuctionsListAdapter: MyAuctionsListAdapter = MyAuctionsListAdapter(ArrayList())

    lateinit var vm: MyAuctionViewModel
    var pendingViewModels = ArrayList<ViewModel>()
    var liveViewModels = ArrayList<ViewModel>()
    var soldOutViewModels = ArrayList<ViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentMyAuctionBinding.bind(inflater.inflate(R.layout.fragment_my_auction, container, false))
        vm = MyAuctionViewModel(mActivity, this)
        binding?.viewModel = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity.resources.getString(R.string.my_auctions), false, false)

        // My Auctions List setup call
        auctionsListSetup()
        if (mActivity.isInternetAvailable()){
            auctionListAPI()
        }
    }


    // Auctions List Setup
    private fun auctionsListSetup(){
        with(binding) {
            rvAuctions.layoutManager = GridLayoutManager(requireContext(), 1)
            rvAuctions.isNestedScrollingEnabled = false
            rvAuctions.adapter = mMyAuctionsListAdapter
        }
    }


    // MY AUCTION LIST API CALL
    private fun auctionListAPI(){
        startAnim()
        mActivity?.viewModel.myAuctionListAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!)
        mActivity?.viewModel.myAuctionsLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    pendingViewModels?.clear()
                    liveViewModels?.clear()
                    soldOutViewModels?.clear()
                    // PENDING LIST
                    if (!it?.oData?.pending.isNullOrEmpty()){
                        binding?.emptyLayout.layoutMain.visibility = View.GONE
                        for (i in 0 until it?.oData?.pending?.size!!) {
                            pendingViewModels.add(MyAuctionsItemViewModel(mActivity, it.oData.pending.get(i)))
                        }
                        mMyAuctionsListAdapter.setList(pendingViewModels)
                    } else {
                        binding?.emptyLayout.layoutMain.visibility = View.VISIBLE
                    }
                    // LIVE LIST
                    if (!it?.oData?.live.isNullOrEmpty()){
                        for (i in 0 until it?.oData?.live?.size!!) {
                            liveViewModels.add(MyAuctionsItemViewModel(mActivity, it.oData.live?.get(i)!!))
                        }
                    }
                    // SOLD OUT LIST
                    if (!it?.oData?.soldout.isNullOrEmpty()){
                        for (i in 0 until it?.oData?.soldout?.size!!) {
                            soldOutViewModels.add(MyAuctionsItemViewModel(mActivity, it.oData.soldout.get(i)))
                        }
                    }
                }
            } else {
                mActivity.myToast(it?.message!!)
            }
        })
    }

    override fun selectedList(id: String?, position: Int) {
        if (id.equals("pending")) {
            if (!pendingViewModels.isNullOrEmpty()) {
                mMyAuctionsListAdapter.setList(pendingViewModels)
                binding?.emptyLayout.layoutMain.visibility = View.GONE
            } else {
                var viewModels = ArrayList<ViewModel>()
                mMyAuctionsListAdapter.setList(viewModels)
                binding.emptyLayout.layoutMain.visibility = View.VISIBLE
            }
        } else if (id.equals("live")) {
            if (!liveViewModels.isNullOrEmpty()) {
                mMyAuctionsListAdapter.setList(liveViewModels)
                binding.emptyLayout.layoutMain.visibility = View.GONE
            } else {
                var viewModels = ArrayList<ViewModel>()
                mMyAuctionsListAdapter.setList(viewModels)
                binding.emptyLayout.layoutMain.visibility = View.VISIBLE
            }
        } else if (id.equals("sold_out")) {
            if (!soldOutViewModels.isNullOrEmpty()) {
                mMyAuctionsListAdapter.setList(soldOutViewModels)
                binding.emptyLayout.layoutMain.visibility = View.GONE
            } else {
                var viewModels = ArrayList<ViewModel>()
                mMyAuctionsListAdapter.setList(viewModels)
                binding.emptyLayout.layoutMain.visibility = View.VISIBLE
            }
        }
    }


}