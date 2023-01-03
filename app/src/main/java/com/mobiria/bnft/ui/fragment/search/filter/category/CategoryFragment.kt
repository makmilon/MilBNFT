package com.mobiria.bnft.ui.fragment.search.filter.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentCategoryBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.search.filter.FilterData
import com.mobiria.bnft.ui.fragment.search.filter.category.adapter.CategoryAdapter
import com.mobiria.bnft.ui.fragment.search.filter.category.adapter.CategoryItemViewModel
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment(), CategoryClickEvent{
    private lateinit var binding: FragmentCategoryBinding
    private val mCategoryAdapter: CategoryAdapter = CategoryAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentCategoryBinding.bind(inflater.inflate(R.layout.fragment_category, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.category), false, false)
        mActivity.toolbarVM.isClearAll.set(true)

        categoryListSetup()

        if (mActivity.isInternetAvailable()) {
            categoryListAPI()
        }
    }

    // Category List Setup
    private fun categoryListSetup(){
        with(binding) {
            rvCategory.layoutManager = LinearLayoutManager(requireContext())
            rvCategory.isNestedScrollingEnabled = false
            rvCategory.adapter = mCategoryAdapter
        }
    }

    // category LIST API CALL
    private fun categoryListAPI(){
        startAnim()
        mActivity.viewModel.categoryApi()
        mActivity.viewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    if (!it?.oData.isNullOrEmpty()){
                        var viewModels = ArrayList<ViewModel>()
                        for (i in 0 until it?.oData?.size!!) {
                            viewModels.add(CategoryItemViewModel(mActivity, it.oData.get(i), this@CategoryFragment))
                        }
                        mCategoryAdapter.setList(viewModels)
                    } else {
                        binding.emptyLayout.layoutMain.visibility = View.VISIBLE
                    }
                }
            } else {
                mActivity.myToast(it?.message!!)
            }
        })
    }


    override fun onCategoryClick(type: String, item: ODataCategory?) {
        val list : MutableList<ViewModel> = mCategoryAdapter.getList()
        var viewModels = ArrayList<ViewModel>()
        for (listItem in list) {
            if (listItem is CategoryItemViewModel) {
                val itemVm = listItem as CategoryItemViewModel
                if (item?.category_id == itemVm.mCategory.get()?.category_id) {
                    itemVm.mIsDefault.set(true)
                    FilterData.category = item?.category_id.toString()
                    viewModels.add(itemVm)

                } else {
                    itemVm.mIsDefault.set(false)
                    viewModels.add(itemVm)
                }
            }
        }
        mCategoryAdapter.setList(viewModels)
    }


}