package com.mobiria.bnft.base

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mobiria.bnft.R
import androidx.activity.viewModels
import com.google.gson.Gson
import com.mobiria.bnft.network.NetworkUtils
import com.mobiria.bnft.network.app_view_model.AppViewModel
import com.mobiria.bnft.ui.auth.ODataLogin
import com.mobiria.bnft.ui.dashboard.navigation_menu.SideMenuViewModel
import com.mobiria.bnft.ui.dashboard.toolbar.ToolBarViewModel
import com.mobiria.bnft.ui.fragment.account.AccountFragment
import com.mobiria.bnft.ui.fragment.home.HomeFragment
import com.mobiria.bnft.ui.fragment.notification.NotificationFragment
import com.mobiria.bnft.utils.*
import com.mobiria.bnft.utils.app_loader.CustomLoaderDialog
import com.mobiria.bnft.utils.preference_data.PreferenceStore
import dev.pinkroom.walletconnectkit.WalletConnectKitConfig


open class BaseActivity : AppCompatActivity() {
    private var pressedTime : Long = 0
    private var mProgressDialog: ProgressDialog? = null
    val viewModel: AppViewModel by viewModels()

    // ToolBar Layout
    lateinit var toolbarVM: ToolBarViewModel
    // Slide menu (Navigation menu)
    lateinit var sideMenuVM: SideMenuViewModel
    // Share Preference
    lateinit var pref: PreferenceStore
    // Login Model Class
    var userLogin: ODataLogin? = null

    // Wallet Connect
    val config = WalletConnectKitConfig(
        context = this,
        bridgeUrl = "wss://bridge.aktionariat.com:8887",
        appUrl = "walletconnectkit.com",
        appName = "BNFT",
        appDescription = "Connect with BNFT"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataFactory.baseActivity = this

        pref = PreferenceStore(this)
        callProfileData()
        val mWindow = window
        mWindow.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

    }

    // RECALL USER SAVED DATA
    fun callProfileData(){
        if (pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
            val gson = Gson()
            val json: String? = pref.getStringData(Constants.APP_USER_DATA)
            userLogin = gson.fromJson(json, ODataLogin::class.java)
        }
    }

    open fun startAnim() {
        if (mProgressDialog == null) {
            mProgressDialog = CustomLoaderDialog.createProgressDialog(this, false)
        } else {
            mProgressDialog?.show()
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }

    open fun getCurrentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(android.R.id.content) as BaseFragment
    }

    fun removeFragments(no: Int) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(
                    fragmentManager.backStackEntryCount - no
                ).id, FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearAllStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
    }

    fun clearBackStack() {
        val fm: FragmentManager = getSupportFragmentManager()
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        }
    }

    fun clearAllStackInstedOfDashBoard() {
        val fm = supportFragmentManager
        for (i in 1 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }
    }

    fun goBack(){
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish()
        } else {
            // myToast("Press back again to exit")
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis();

    }


    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun replaceFragment(fragment: Fragment){
        replaceFragment(fragment, false, true, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean){
        replaceFragment(fragment, isAdd, true, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean,addtobs: Boolean){
        replaceFragment(fragment, isAdd, addtobs, false)
    }

    fun replaceFragment(fragment: Fragment, isAdd: Boolean, addtobs: Boolean, forceWithoutAnimation: Boolean){
        replaceFragment(fragment, isAdd, addtobs, forceWithoutAnimation, null)
    }

    fun replaceFragment(
        fragment: Fragment,
        isAdd: Boolean,
        addtobs: Boolean,
        forceWithoutAnimation: Boolean,
        transition: Transition?
    ) {
        Thread {
            if (!isLastStackedFragment(fragment)) {
                if (fragment is HomeFragment || fragment is NotificationFragment || fragment is AccountFragment) {
                    val fm: FragmentManager = supportFragmentManager
                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
                }
                val ft =  supportFragmentManager.beginTransaction()
                val tag = fragment.javaClass.name.toString()
                ft.setReorderingAllowed(true)
                if (!forceWithoutAnimation) {
                    if (AndroidUtility.isAndroid5()) {
                        if (transition != null) {
                            fragment.enterTransition = transition
                        } else {
                            fragment.enterTransition = TransitionUtil().slide(Gravity.RIGHT)
                        }
                    } else {
                        ft.setCustomAnimations(
                            R.anim.pull_in_right,
                            R.anim.push_out_left,
                            R.anim.pull_in_left,
                            R.anim.push_out_right
                        )
                    }
                } else {
                    ft.setCustomAnimations(
                        R.anim.from_down,
                        R.anim.from_up,
                        R.anim.from_down,
                        R.anim.from_up
                    )
                }
                if (isAdd){
                    ft.add(R.id.fragment_view, fragment, tag)
                } else {
                    ft.replace(R.id.fragment_view, fragment, tag)
                }
                if (addtobs){
                    ft.addToBackStack(tag)
                }
                ft.commit()

            }
            try {
                runOnUiThread {
                    YoYo.with(Techniques.SlideInRight)
                        .duration(700)
                        .repeat(0)
                        .playOn(findViewById<View>(R.id.fragment_view))
                }
            } catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }


    fun isLastStackedFragment(fragment: Fragment): Boolean {
        var status = false
        try {
            val index: Int = supportFragmentManager.backStackEntryCount - 1
            val tag: String =
                supportFragmentManager.getBackStackEntryAt(index).name.toString()
            val frg: Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if (frg?.tag.toString() == fragment.javaClass.name.toString()) {
                status = true
            }
        } catch (e: Exception) {
            Log.e("isLastStackedFragment: ", e.toString())
        }
        return status
    }

    fun isInternetAvailable(): Boolean {
        return if(NetworkUtils.isNetworkConnected(this)) {
            true
        } else {
            myToast(resources.getString(R.string.err_internet))
            false
        }
    }
}