package com.mobiria.bnft.ui.fragment.favorite

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.favorite.interfaces.FavoriteClickEvent
import com.mobiria.bnft.ui.fragment.favorite.model.FavoriteItem
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment
import com.mobiria.bnft.utils.Constants

class FavoriteItemViewModel(val mActivity: BaseActivity, mItem: FavoriteItem, val mCallback: FavoriteClickEvent) : ViewModel {

    @JvmField
    val mFavoriteItem = ObservableField<FavoriteItem>()

    @JvmField
    val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mPrice = ObservableField<String>()

    init {
        mFavoriteItem.set(mItem)
        with(mItem) {
            mTitle.set(name)
            mPrice.set(price+" "+Constants.CURRENCY)
            mImage.set(image_path)
        }
    }

    fun onIsFavoriteItem(view: View){
        mCallback?.onFavoriteItem(mFavoriteItem.get())
    }

    fun onItemClick(view: View) {
        var fragment = DetailsFragment()
        fragment.newInstance(mFavoriteItem.get()?.id.toString())
        mActivity.replaceFragment(fragment)
    }

    override fun close() {
    }
}