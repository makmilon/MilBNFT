package com.mobiria.bnft.ui.fragment.thank_you

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentThankYouBinding
import com.mobiria.bnft.ui.dashboard.MainActivity

class ThankYouFragment : BaseFragment() {
    private lateinit var binding: FragmentThankYouBinding

    private var bookingId: String = ""

    fun newInstance(bookingId: String): ThankYouFragment{
        this.bookingId = bookingId
        return ThankYouFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentThankYouBinding.bind(inflater.inflate(R.layout.fragment_thank_you, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(false, false, false, false, false,
            "", false, false)

        binding?.textview4.text = bookingId
        binding?.btnBackToHome?.setOnClickListener {
            try {
                (mActivity as MainActivity).homeClick()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}