package com.mobiria.bnft.ui.fragment.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentFilterBinding
import com.mobiria.bnft.ui.fragment.search.filter.category.CategoryFragment
import com.mobiria.bnft.ui.fragment.search.filter.sort_by.SortByFragment
import com.mobiria.bnft.ui.fragment.search.filter.status.StatusFragment

class FilterFragment : BaseFragment() {
    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentFilterBinding.bind(inflater.inflate(R.layout.fragment_filter, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity.resources.getString(R.string.search), false, false)

        onClickHandler()
    }

    private fun onClickHandler(){
        binding.tvCategory.setOnClickListener {
            mActivity.replaceFragment(CategoryFragment())
        }
        binding.tvSortBy.setOnClickListener {
            mActivity.replaceFragment(SortByFragment())
        }
        binding.tvStatus.setOnClickListener {
            mActivity.replaceFragment(StatusFragment())
        }

        binding.btnApply.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
}