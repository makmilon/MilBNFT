package com.mobiria.bnft.ui.fragment.search.filter.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentStatusBinding

class StatusFragment : BaseFragment(){
    private lateinit var binding: FragmentStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStatusBinding.bind(inflater.inflate(R.layout.fragment_status, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.status), false, false)
        mActivity.toolbarVM.isClearAll.set(true)

    }
}