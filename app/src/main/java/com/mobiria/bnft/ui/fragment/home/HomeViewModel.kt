package com.mobiria.bnft.ui.fragment.home

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesFragment

class HomeViewModel(val mActivity: BaseActivity, val mCallBack: ListSelector) : ViewModel {

    @JvmField
    val mTabHud = ObservableField<Boolean>()


    init {
        mTabHud.set(true)
    }

    // Hoodies Click Event
    fun onHodClick(view: View){
        mTabHud.set(true)
        mCallBack.selectedList("hod", 1)
    }
    // Auctions Click Event
    fun onAuctionsClick(view: View){
        mTabHud.set(false)
     mCallBack.selectedList("auction", 2)
    }

    // Collection left Button
    fun onCollectionLeftClick(view: View) {

    }
    // Collection Right Button
    fun onCollectionRightClick(view: View) {

    }
    // Dropped View All Click Event
    fun onDroppedViewAllClick(view: View) {

    }

    // Dropped View All Click Event
    fun onCommanViewAllClick(view: View) {

    }

    // Popular Collections View All Click Event
    fun onPopularCollectionViewAllClick(view: View) {
     try {
      val fragment = HoodiesFragment()
      fragment.newInstance("Popular Collection", "")
      mActivity.replaceFragment(fragment)
     } catch (e: Exception){
      e.printStackTrace()
     }
    }




    override fun close() {
    }
}