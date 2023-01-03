package com.mobiria.bnft.ui.fragment.cms.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentFaqBinding
import com.mobiria.bnft.ui.fragment.cms.faq.adapter.FaqsAdapter
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class FaqFragment(val title: String) : BaseFragment() {
    private lateinit var binding: FragmentFaqBinding
    private var mFaqsdapter: FaqsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentFaqBinding.bind(inflater.inflate(R.layout.fragment_faq, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            title, false, false)

        var layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL , false)
        mFaqsdapter = FaqsAdapter()
        binding!!.rvFaqs.layoutManager = layoutManager
        binding!!.rvFaqs.adapter = mFaqsdapter
        if (mActivity.isInternetAvailable()){
            faqAPI()
        }
    }

    // FAQ API
    private fun faqAPI(){
        startAnim()
        mActivity?.viewModel.faqApi()
        mActivity?.viewModel.faqLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    mFaqsdapter?.setFaqsList(mActivity!!, it?.oData)
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }
}