package com.mobiria.bnft.ui.fragment.cms.contact_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentContactUsBinding
import com.mobiria.bnft.ui.fragment.account.edit_profile.EditProfileViewModel
import com.mobiria.bnft.ui.fragment.cms.CMSViewModel
import com.mobiria.bnft.utils.Validation
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class ContactUsFragment : BaseFragment() {
    private lateinit var binding: FragmentContactUsBinding

    lateinit var vm: ContactUsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentContactUsBinding.bind(inflater.inflate(R.layout.fragment_contact_us, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.contact_us), false, false)

        try {
            val userData = mActivity.userLogin
            vm = ContactUsViewModel(mActivity, userData!!)
            binding.viewModel = vm
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.btnSubmit.setOnClickListener {
            if (validated()) {
                contactUsAPI(
                    binding?.etFullName.text.toString(),
                    binding?.etEmail.text.toString(),
                    binding?.countryCodeHolder.selectedCountryCode.toString(),
                    binding?.etMobileNumber.text.toString(),
                    binding?.etMessage.text.toString()
                )
            }
        }
    }

    private fun validated(): Boolean {
        return (!Validation.checkIsEmpty(
            binding?.etFullName,
            binding?.etEmail,
            binding?.etMobileNumber,
            binding?.etMessage,
        ))
        return true
    }


    // Contact Us api
    private fun contactUsAPI(name: String, email: String, dialCode: String, phone: String, message: String){
        startAnim()
        mActivity?.viewModel.contactUsAPI(name, email, dialCode, phone, message)
        mActivity?.viewModel.contactUsLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    try {
                        mActivity.myToast(it?.message!!)
                        binding.etMessage.setText("")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

}