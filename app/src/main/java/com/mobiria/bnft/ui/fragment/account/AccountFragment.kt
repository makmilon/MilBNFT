package com.mobiria.bnft.ui.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentAccountBinding
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import com.mobiria.bnft.utils.ui_navigation.AppNavigator
import kotlinx.coroutines.launch

class AccountFragment : BaseFragment(), ListSelector {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var accountVM: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAccountBinding.bind(inflater.inflate(R.layout.fragment_account, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar Setup
        mActivity.toolbarVM.manageToolBar(false)
        // bind view model class
        accountVM = AccountViewModel(mActivity, this@AccountFragment)
        binding.viewModel = accountVM
    }

    override fun selectedList(id: String?, position: Int) {
        if(id.equals("logout")) {
           logoutAPI()
        }
    }

    // Logout api
    private fun logoutAPI(){
        startAnim()
        mActivity?.viewModel.logoutAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!)
        mActivity?.viewModel.logoutLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    pref.clearPrefrenceData()
                    AppNavigator.navigateToLoginActivity(mActivity, true)
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }
}