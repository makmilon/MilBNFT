package com.mobiria.bnft.ui.comman.carousel

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.model.CarouselModel
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment

class CarouselItemViewModel(val mActivity: BaseActivity, val item: CarouselModel) : ViewModel {

   @JvmField
   val mImage = ObservableField<Int>()

    init {
    }

    override fun close() {
    }
}