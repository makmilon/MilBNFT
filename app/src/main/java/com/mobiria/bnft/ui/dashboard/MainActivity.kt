package com.mobiria.bnft.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.databinding.ActivityMainBinding
import com.mobiria.bnft.databinding.NavigationMenuBinding
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.auth.LoginActivity
import com.mobiria.bnft.ui.dashboard.bottom_nemu.BottomMenuInterface
import com.mobiria.bnft.ui.dashboard.bottom_nemu.BottomMenuViewModel
import com.mobiria.bnft.ui.dashboard.navigation_menu.SideMenuViewModel
import com.mobiria.bnft.ui.dashboard.toolbar.ToolBarViewModel
import com.mobiria.bnft.ui.dashboard.toolbar.ToolbarInterface
import com.mobiria.bnft.ui.fragment.account.AccountFragment
import com.mobiria.bnft.ui.fragment.auctions.AuctionFragment
import com.mobiria.bnft.ui.fragment.home.HomeFragment
import com.mobiria.bnft.ui.fragment.my_order.order_detail.OrderDetailsFragment
import com.mobiria.bnft.ui.fragment.notification.NotificationFragment
import com.mobiria.bnft.ui.fragment.notification.public_notification.PublicNotificationFragment
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment
import com.mobiria.bnft.ui.fragment.search.SearchFragment
import com.mobiria.bnft.utils.AndroidUtility
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.myToast
import com.mobiria.bnft.utils.ui_navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomMenuInterface, ToolbarInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationBinding: NavigationMenuBinding
    private lateinit var mainVM: MainViewModel
    private lateinit var bottomMenuVM: BottomMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        navigationMenuSetup()
    }


    private fun init(){
        mainVM = MainViewModel(this)
        binding.dashboard.viewModel = mainVM
        mainVM.mBottomMenuInterface = this

        toolbarVM = ToolBarViewModel(this, binding.drawerLayout, this)
        binding.dashboard.toolbar.toolbarViewModel = toolbarVM

        bottomMenuVM = BottomMenuViewModel(this)
        binding.dashboard.bottomMenu.bottomViewViewModel = bottomMenuVM
        bottomMenuVM.mBottomMenuInterface = this

        homeClick()

        val bundle1 = intent.extras
        if (bundle1!=null && bundle1["deepLinkData"]!=null &&  bundle1["deepLinkData"]!="" && bundle1["deepLinkData"]!="null"){
            if(pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
                val deepLink: String =  bundle1["deepLinkData"].toString()
                val productId = (deepLink.split("#")[deepLink.split("#").lastIndex])
                // val storeId_ = (deepLink.split("#")[deepLink.split("#").lastIndex-1])
                var fragment = DetailsFragment()
                fragment.newInstance(productId)
                replaceFragment(fragment)
            } else {
                AppNavigator.navigateToLoginActivity(this, false, "home")
            }
        } else {
            if (bundle1 != null && bundle1["type"] != null) {
                if (bundle1["type"] != null) {
                    val bundle = Bundle()
                    if (bundle1["type"] == "public-notification") {
                        val fragment = PublicNotificationFragment()
                        fragment.newInstance(
                            bundle1["title"].toString(),
                            bundle1["message"].toString(),
                            bundle1["image"].toString()
                        )
                        replaceFragment(fragment)
                    } else if (bundle1["type"] == "order_placed") {
                        val fragment = OrderDetailsFragment()
                        fragment.newInstance(bundle1["order_id"].toString(), "#"+bundle1["order_id"].toString())
                        replaceFragment(fragment)
                    } else if (bundle1["type"] == "order_status") {
                        val fragment = OrderDetailsFragment()
                        fragment.newInstance(bundle1["order_id"].toString(), "#"+bundle1["order_id"].toString())
                        replaceFragment(fragment)
                    } else if (bundle1["type"] == "bid_place") {
                    } else if (bundle1["type"] == "bid_end") {
                    } else if (bundle1["type"] == "bid_winner") {
                    } else if (bundle1["type"] == "resell_alert") {
                        try {
                            val fragment = DetailsFragment(true)
                            fragment.newInstance(bundle1["order_id"].toString())
                            replaceFragment(fragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        if (pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!){
            bottomMenuVM.mProfileImage.set(userLogin?.user_image)
        }


        try {
            val moveTo: String? = intent.getStringExtra(Constants.KEY_MOVE_TO)
            val ids: String? = intent.getStringExtra(Constants.KEY_PRODUCT_ID)
            if(moveTo.equals("product_details")) {
                var fragment = DetailsFragment()
                fragment.newInstance(ids)
                replaceFragment(fragment)
            } else if(moveTo.equals("product_details_auction")) {
                var fragment = DetailsFragment(true, true)
                fragment.newInstance(ids)
                replaceFragment(fragment)
            } else {

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun homeClick(){
        bottomMenuVM.onHomeClick(binding.root)
    }


    private fun navigationMenuSetup() {
        navigationBinding =
            NavigationMenuBinding.bind(LayoutInflater.from(this)
                .inflate(R.layout.navigation_menu, null, false))
        binding.navView.addHeaderView(navigationBinding.root)
        sideMenuVM = SideMenuViewModel(this, binding.drawerLayout)
        navigationBinding.sideMenuViewModel = sideMenuVM
    }

    override fun selectedBottomMenuItem(item: String) {
        val fragment: Fragment? = getSupportFragmentManager().findFragmentById(R.id.fragment_view)
        if (item.equals("home")) {
            if (fragment is HomeFragment) { } else {
                replaceFragment(HomeFragment())
            }
            manageBottomView(binding.dashboard.bottomMenu.ivHome, binding.dashboard.bottomMenu.tvHome)
        } else if (item.equals("auction")) {
            if (fragment is AuctionFragment) { } else {
                replaceFragment(AuctionFragment())
            }
            manageBottomView(binding.dashboard.bottomMenu.ivAuctions, binding.dashboard.bottomMenu.tvAuction)
        } else if (item.equals("search")) {
            if (fragment is SearchFragment) { } else {
                replaceFragment(SearchFragment())
            }
            manageBottomView(binding.dashboard.ivSearch, binding.dashboard.bottomMenu.tvSearch)
        } else if (item.equals("notification")) {
            if (pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
                if (fragment is NotificationFragment) {
                } else {
                    replaceFragment(NotificationFragment())
                }
                manageBottomView(
                    binding.dashboard.bottomMenu.ivNotification,
                    binding.dashboard.bottomMenu.tvNotification
                )
            } else {
                AppNavigator.navigateToLoginActivity(this, false)
            }
        } else if (item.equals("account")) {
            if (pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
                if (fragment is AccountFragment) { } else {
                    replaceFragment(AccountFragment())
                }
                manageBottomView(null, binding.dashboard.bottomMenu.tvAccount)
            } else {
                AppNavigator.navigateToLoginActivity(this, false)
            }
        } else {
        }
    }


    fun manageBottomView(imageView: ImageView? = null, textView: TextView){
        AndroidUtility.setImageTintUnCheck(binding.dashboard.bottomMenu.ivHome, this)
        AndroidUtility.setImageTintUnCheck(binding.dashboard.bottomMenu.ivAuctions, this)
        AndroidUtility.setImageTintUnCheck(binding.dashboard.bottomMenu.ivNotification, this)
        AndroidUtility.setImageTintUnCheck(binding.dashboard.bottomMenu.ivAccount, this)
        AndroidUtility.setImageTintUnCheck(binding.dashboard.ivSearch, this)

        AndroidUtility.textColorUnselected(binding.dashboard.bottomMenu.tvHome, this)
        AndroidUtility.textColorUnselected(binding.dashboard.bottomMenu.tvAuction, this)
        AndroidUtility.textColorUnselected(binding.dashboard.bottomMenu.tvSearch, this)
        AndroidUtility.textColorUnselected(binding.dashboard.bottomMenu.tvNotification, this)
        AndroidUtility.textColorUnselected(binding.dashboard.bottomMenu.tvAccount, this)

        if (imageView != null) {
            AndroidUtility.setImageTintChecked(imageView!!, this)
        }
        AndroidUtility.textColorWhite(textView, this)
    }

    override fun onBackPressed() {
        hideSoftKeyboard(this)
        val fragment: Fragment? = getSupportFragmentManager().findFragmentById(R.id.fragment_view)
        if(fragment is HomeFragment) {
            goBack()
        } else if (fragment is AuctionFragment || fragment is SearchFragment || fragment is NotificationFragment || fragment is AccountFragment) {
            bottomMenuVM.onHomeClick(binding.root)
        } else {
            clearBackStack()
        }
    }

    var myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.getStringExtra("action")
            val code = intent.getStringExtra("code")
            val message = intent.getStringExtra("message")
            stopAnim()
            if (code.equals("401")) {
                pref.clearPrefrenceData()
                pref.saveBooleanData(Constants.APP_LOGIN_STATUS, false)
                val intent= Intent(this@MainActivity, LoginActivity::class.java)
                intent.putExtra("code", code)
                intent.putExtra("message", message)
                intent.putExtra("action", action)
                startActivity(intent)
                finish()
            } else {
                CustomDialog.showDialogSessionExpired(this@MainActivity, code, message, object : DialogListener {
                    override fun setValue(value: String, position: Int) {
                        stopAnim()
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(myReceiver, IntentFilter("API-HITTING"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

    override fun toolBarItemClick(type: String, id: String) {
        if (type.equals("favorite")) {
            isFavoriteAPI(id)
        }
    }

    // is Favorite (add/remove) api
    private fun isFavoriteAPI(productId: String){
        startAnim()
        viewModel.isFavoriteAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productId)
        viewModel.isFavoriteLiveData.observe(this, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    toolbarVM.setFavoriteTrue(it?.oData?.action_status!!)
                }
            } else {
                myToast(it?.message!!)
            }
        })
    }

}