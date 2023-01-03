package com.mobiria.bnft.ui.fragment.search

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentSearchBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesItemViewModel
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesListAdapter
import com.mobiria.bnft.ui.fragment.search.filter.FilterData
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val mHoodiesListAdapter: HoodiesListAdapter = HoodiesListAdapter(ArrayList())
    var viewModels = ArrayList<ViewModel>()

    private var page: Int = 1
    private var isPopular: String = "0"
    private var keyword: String= ""

    var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.search), false, false)
        mActivity.toolbarVM.isFilter.set(true)

        hoodiesListSetup()

        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    keyword = s.toString()
                    Log.e("search_text", keyword)
                    page = 1

                    if (timer != null) {
                        timer?.cancel()
                        timer = null
                    }
                    delayAPi()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        binding?.nestedView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                if (mActivity.isInternetAvailable()) {
                    productListAPI()
                }
            }
        })

    }

    fun delayAPi(){
        timer = object : CountDownTimer(1500, 500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                productListAPI()
            }
        }
        timer?.start()
    }

    // Hoodies List Setup
    private fun hoodiesListSetup(){
        with(binding) {
            rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
            rvSearch.isNestedScrollingEnabled = false
            rvSearch.adapter = mHoodiesListAdapter
        }
    }


    override fun onResume() {
        super.onResume()
        productListAPI()
    }


    // PRODUCT LIST API CALL
    private fun productListAPI(){
        startAnim()
        mActivity.viewModel.productListListAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, page.toString(),
            FilterData.status, keyword, FilterData.sortBy, "", FilterData.category, isPopular)
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
                            val viewList = ArrayList<ViewModel>()
                            mHoodiesListAdapter.setList(viewList)
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