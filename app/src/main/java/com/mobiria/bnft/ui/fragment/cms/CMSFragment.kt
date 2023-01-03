package com.mobiria.bnft.ui.fragment.cms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentCmsBinding
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import com.mobiria.bnft.utils.ui_navigation.AppNavigator
import kotlinx.coroutines.launch

class CMSFragment(val title: String) : BaseFragment() {
    private lateinit var binding: FragmentCmsBinding
    lateinit var vm: CMSViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentCmsBinding.bind(inflater.inflate(R.layout.fragment_cms, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            title, false, false)


        if (title.equals("Privacy Policy")) {
            if (mActivity.isInternetAvailable()){
                privacyPolicyAPI()
            }
        } else if (title.equals("About Us")){
            if (mActivity.isInternetAvailable()){
                aboutUsAPI()
            }
        } else if (title.equals("Terms & Conditions")){
            if (mActivity.isInternetAvailable()){
                termsAndConditionApi()
            }
        } else {

        }
    }

    // PRIVACY POLICY api
    private fun privacyPolicyAPI(){
        startAnim()
        mActivity?.viewModel.privacyPolicyApi()
        mActivity?.viewModel.privacyPolicyLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    vm = CMSViewModel(mActivity, it?.oData!!)
                    binding?.viewmodel = vm
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

    // PRIVACY POLICY api
    private fun aboutUsAPI(){
        startAnim()
        mActivity?.viewModel.aboutUsApi()
        mActivity?.viewModel.aboutUsLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    vm = CMSViewModel(mActivity, it?.oData!!)
                    binding?.viewmodel = vm
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

    // TERMS AND CONDITIONS API
    private fun termsAndConditionApi(){
        startAnim()
        mActivity?.viewModel.termsAndConditionApi()
        mActivity?.viewModel.termsAndConditionLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    vm = CMSViewModel(mActivity, it?.oData!!)
                    binding?.viewmodel = vm
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

}