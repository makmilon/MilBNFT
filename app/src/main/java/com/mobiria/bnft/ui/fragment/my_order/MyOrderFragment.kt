package com.mobiria.bnft.ui.fragment.my_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentMyOrderBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch


class MyOrderFragment : BaseFragment() {
    private lateinit var binding: FragmentMyOrderBinding
    private val mMyOrderListAdapter: MyOrderListAdapter = MyOrderListAdapter(ArrayList())

    val viewModels = ArrayList<ViewModel>()
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentMyOrderBinding.bind(inflater.inflate(R.layout.fragment_my_order, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.my_orders), false, false)

        // Auctions List setup call
        auctionsListSetup()

        // MY ORDER LIST API CALLING HERE
        if (mActivity.isInternetAvailable()) {
            myOrderListAPI()
        }

        // adding on scroll change listener method for our nested scroll view.
        binding?.nestedView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                if (mActivity.isInternetAvailable()) {
                    myOrderListAPI()
                }
            }
        })
    }


    // Auctions List Setup
    private fun auctionsListSetup(){
        with(binding) {
            rvMyOrder.layoutManager = LinearLayoutManager(requireContext())
            rvMyOrder.isNestedScrollingEnabled = false
            rvMyOrder.adapter = mMyOrderListAdapter
        }

    }

    // MY Order LIST API CALL
    private fun myOrderListAPI(){
        startAnim()
        mActivity?.viewModel.myOrdersAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, page.toString())
        mActivity?.viewModel.myOrderLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    if (!it?.oData?.sales_history.isNullOrEmpty()) {
                        if (page == 1){
                            viewModels.clear()
                        }
                        binding?.nestedView?.visibility = View.VISIBLE
                        binding?.emptyLayout?.layoutMain?.visibility = View.GONE
                        for (i in 0 until it?.oData?.sales_history!!.size) {
                            viewModels.add(MyOrderItemViewModel(mActivity, it?.oData?.sales_history.get(i), true))
                        }
                        if (page == 1) {
                            mMyOrderListAdapter.setList(viewModels)
                        } else {
                            mMyOrderListAdapter.addList(viewModels)
                        }
                        page = page.plus(1)
                    } else {
                        if (page == 1) {
                            val viewModels = ArrayList<ViewModel>()
                            mMyOrderListAdapter.setList(viewModels)
                            binding?.emptyLayout?.layoutMain?.visibility = View.VISIBLE
                            binding?.nestedView?.visibility = View.GONE
                        }
                    }
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }
}