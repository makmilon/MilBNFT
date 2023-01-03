package com.mobiria.bnft.ui.dashboard.navigation_menu

import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.fragment.cms.CMSFragment
import com.mobiria.bnft.ui.fragment.cms.contact_us.ContactUsFragment

class SideMenuViewModel(val mActivity: BaseActivity, val drawerlayout: DrawerLayout) : ViewModel() {

    fun onHomeClick(view: View) {
        closeNav()
    }
    fun onAboutUsClick(view: View) {
        closeNav()
        mActivity?.replaceFragment(CMSFragment("About Us"))
    }
    fun onFaqClick(view: View) {
        closeNav()
        mActivity?.replaceFragment(CMSFragment("FAQs"))
    }
    fun onTermsConditionClick(view: View) {
        closeNav()
        mActivity?.replaceFragment(CMSFragment("Terms & Conditions"))
    }
    fun onPrivacyPolicyClick(view: View) {
        closeNav()
        mActivity?.replaceFragment(CMSFragment("Privacy Policy"))
    }
    fun onContactusClick(view: View) {
        closeNav()
        mActivity?.replaceFragment(ContactUsFragment())
    }
    fun onShareClick(view: View) {
        closeNav()
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
}