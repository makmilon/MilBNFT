package com.mobiria.bnft.ui.fragment.hoodies

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
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class HoodiesFragment : BaseFragment() {
    private lateinit var binding: FragmentAuctionBinding
    private val mHoodiesListAdapter: HoodiesListAdapter = HoodiesListAdapter(ArrayList())
    var viewModels = ArrayList<ViewModel>()

    private var page: Int = 1
    private var mTitle: String = "Product"
    private var categoryId: String = ""
    private var isPopular: String = "0"


    fun newInstance(title: String, categoryId: String): HoodiesFragment {
        this.mTitle = title
        this.categoryId = categoryId
        return HoodiesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            mTitle, false, false)

        // Hoodies List setup call
        hoodiesListSetup()
        if (mActivity?.isInternetAvailable()){
            if(mTitle.equals("Popular Collection")) {
                isPopular = "1"
            }
            productListAPI()
        }
        onClickHandler()
    }

    private fun onClickHandler(){
        binding.rvAuctions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) { //SCROLL_STATE_IDLE
                    Log.e("scrolling", "List size  : " +viewModels.size +"  "+recyclerView.scrollState)
                    if (viewModels.size == recyclerView.scrollState) {
                        productListAPI()
                    }
                }
            }
        })
    }


    // Hoodies List Setup
    private fun hoodiesListSetup(){
        with(binding) {
            rvAuctions.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAuctions.isNestedScrollingEnabled = false
            rvAuctions.adapter = mHoodiesListAdapter
        }
    }

    // PRODUCT LIST API CALL
    private fun productListAPI(){
        startAnim()
        mActivity.viewModel.productListListAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, page.toString(),
           "", "", Constants.SORT_BY, "", categoryId, isPopular) // Constants.SALE_TYPE_BUY
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
                            viewModels.add(HoodiesItemViewModel(mActivity, false, it.oData.result.get(i)))
                        }
                        if (page == 1) {
                            mHoodiesListAdapter.setList(viewModels)
                        } else {
                            mHoodiesListAdapter.addList(viewModels)
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