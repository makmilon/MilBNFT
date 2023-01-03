package com.mobiria.bnft.ui.fragment.search.filter.sort_by

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentSortByBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.search.filter.FilterData
import com.mobiria.bnft.ui.fragment.search.filter.category.adapter.CategoryAdapter
import com.mobiria.bnft.ui.fragment.search.filter.category.adapter.CategoryItemViewModel
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class SortByFragment : BaseFragment(){
    private lateinit var binding: FragmentSortByBinding
    private val mCategoryAdapter: CategoryAdapter = CategoryAdapter(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentSortByBinding.bind(inflater.inflate(R.layout.fragment_sort_by, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.sort_by), false, false)
        mActivity.toolbarVM.isClearAll.set(true)

    }
}