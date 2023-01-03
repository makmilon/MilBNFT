package com.mobiria.bnft.ui.dashboard.toolbar

import android.content.Intent
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.ObservableField
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.firebase.FirebaseUserHandler
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.ui.fragment.search.filter.FilterFragment
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.ui_navigation.AppNavigator

class ToolBarViewModel(val mActivity: BaseActivity, val drawerlayout: DrawerLayout, val mCallBack: ToolbarInterface) : ViewModel() {

    @JvmField
    val isToolbar = ObservableField<Boolean>()

    @JvmField
    val isMenu = ObservableField<Boolean>()

    @JvmField
    val isLogo = ObservableField<Boolean>()

    @JvmField
    val isBack = ObservableField<Boolean>()

    @JvmField
    val isTitle = ObservableField<Boolean>()

    @JvmField
    val titleText = ObservableField<String>()

    @JvmField
    val isFavorite = ObservableField<Boolean>()

    @JvmField
    val isFilter = ObservableField<Boolean>()

    @JvmField
    val isFavoriteTrue = ObservableField<Boolean>()

    @JvmField
    val isShare = ObservableField<Boolean>()

    @JvmField
    val isAllClear = ObservableField<Boolean>()

    @JvmField
    val isClearAll = ObservableField<Boolean>()

    @JvmField
    val mShareLink = ObservableField<String>()

    @JvmField
    val mProductId = ObservableField<String>()

    fun manageToolBar(isToolbar: Boolean = false, isMenu: Boolean = false, isLogo: Boolean = false, isBack: Boolean = false,
                      isTitle: Boolean = false, titleText: String = "", isFavorite: Boolean = false, isShare: Boolean = false,
                      productId: String="",shareLink: String? = "") {

        this.isClearAll.set(false)
        this.isAllClear.set(false)
        this.isFilter.set(false)
        this.isToolbar.set(isToolbar)
        this.isMenu.set(isMenu)
        this.isLogo.set(isLogo)
        this.isBack.set(isBack)
        this.isTitle.set(isTitle)
        this.titleText.set(titleText)
        this.isFavorite.set(isFavorite)
        this.isShare.set(isShare)
        this.mProductId.set(productId)
        this.mShareLink.set(shareLink)
        if(isBack){
           this.isTitle.set(false)
        }
    }

    fun onMenuClick(view: View) {
        closeNav()
    }

    fun onFavoriteClick(view: View) {
        if (mActivity.pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            if (!mProductId.get().isNullOrEmpty()) {
                mCallBack.toolBarItemClick("favorite", mProductId.get().toString())
            }
        }  else {
            AppNavigator.navigateToLoginActivity(mActivity, false, "product_details", mProductId.get().toString())
        }
    }
    fun onFilterClick(view: View) {
        mActivity.replaceFragment(FilterFragment())
    }

    fun setFavoriteTrue(status: Int){
        if (status == 1) {
            isFavoriteTrue.set(true)
        } else {
            isFavoriteTrue.set(false)
        }
    }

    fun onShareClick(view: View) {
        if (!mShareLink.get().isNullOrEmpty()) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mShareLink.get())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            mActivity.startActivity(shareIntent)
        }
    }

    fun onBackPressedEvent(view: View){
        mActivity?.onBackPressed()
    }

    fun closeNav() {
        if (drawerlayout != null) {
            if (drawerlayout.isDrawerOpen(GravityCompat.END)) {
                drawerlayout.closeDrawer(GravityCompat.END)
            } else {
                drawerlayout.openDrawer(GravityCompat.END)
            }
        }
    }

    fun onAllClearClick(view: View) {
        CustomDialog.showLogoutDialog(
            mActivity,
            mActivity.resources?.getString(R.string.sure_delete_notif),
            object : DialogListener {
                override fun setValue(value: String, position: Int) {
                    try {
                        FirebaseUserHandler.deleteAllNotification(
                            mActivity, mActivity.userLogin?.firebase_user_key!!,
                        )
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
    }

    fun onClearAll(view: View) {

    }
}