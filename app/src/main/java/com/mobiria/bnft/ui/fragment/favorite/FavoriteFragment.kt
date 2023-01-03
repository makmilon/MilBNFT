package com.mobiria.bnft.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentFavoriteBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.favorite.adapter.FavoriteListAdapter
import com.mobiria.bnft.ui.fragment.favorite.interfaces.FavoriteClickEvent
import com.mobiria.bnft.ui.fragment.favorite.model.FavoriteItem
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class FavoriteFragment : BaseFragment(), FavoriteClickEvent {
    private lateinit var binding: FragmentFavoriteBinding
    private val mFavoriteListAdapter: FavoriteListAdapter = FavoriteListAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentFavoriteBinding.bind(inflater.inflate(R.layout.fragment_favorite, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.favorites), false, false)

        // Auctions List setup call
        favoriteListSetup()
        if (mActivity.isInternetAvailable()){
            favoriteAPI()
        }
    }

    // Auctions List Setup
    private fun favoriteListSetup(){
        with(binding) {
            rvFavorite.layoutManager = GridLayoutManager(requireContext(), 2)
            rvFavorite.isNestedScrollingEnabled = false
            rvFavorite.adapter = mFavoriteListAdapter
        }
    }

    private fun favoriteAPI(){
        startAnim()
        mActivity?.viewModel.myFavoriteAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!)
        mActivity?.viewModel.myFavoriteLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    if (!it?.oData?.data.isNullOrEmpty()){
                        binding?.emptyLayout?.layoutMain.visibility = View.GONE
                        val viewModels = ArrayList<ViewModel>()
                        for (i in 0..(it?.oData?.data?.size!!-1)) {
                            viewModels.add(FavoriteItemViewModel(mActivity, it?.oData?.data?.get(i), this@FavoriteFragment))
                        }
                        mFavoriteListAdapter.setList(viewModels)
                    } else {
                        binding?.emptyLayout?.layoutMain.visibility = View.VISIBLE
                    }
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

    // is Favorite (add/remove) api
    private fun isFavoriteAPI(productId: String){
        startAnim()
        mActivity?.viewModel.isFavoriteAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productId)
        mActivity?.viewModel.isFavoriteLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                   if (mActivity?.isInternetAvailable()){
                       favoriteAPI()
                   }
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }


    // on Favorite click event
    override fun onFavoriteItem(item: FavoriteItem?) {
        try {
            if (mActivity.isInternetAvailable()){
                isFavoriteAPI(item?.id.toString())
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}