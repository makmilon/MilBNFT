package com.mobiria.bnft.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.databinding.ActivityLoginBinding
import com.mobiria.bnft.firebase.FirebaseToken
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.dashboard.MainActivity
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.myToast
import com.mobiria.bnft.utils.startNewActivity
import com.mobiria.bnft.utils.ui_navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import dev.pinkroom.walletconnectkit.WalletConnectKit
import kotlinx.coroutines.launch
import org.walletconnect.Session

@AndroidEntryPoint
class LoginActivity : BaseActivity(), LoginInterface, Session.Callback{
    private lateinit var binding: ActivityLoginBinding
    private lateinit var vm: LoginViewModel

    var moveTo: String?= ""
    var ids: String?= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()

        // User Session Expired
        try {
            val code: String? = intent.getStringExtra("code")
            val message: String? = intent.getStringExtra("message")
            val action: String? = intent.getStringExtra("action")
            moveTo = intent.getStringExtra(Constants.KEY_MOVE_TO)
            ids = intent.getStringExtra(Constants.KEY_PRODUCT_ID)
            if (!code.isNullOrEmpty()) {
                CustomDialog.showDialogSessionExpired(this, code, message, object : DialogListener {
                    override fun setValue(value: String, position: Int) {
                        stopAnim()
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun init(){
        vm = LoginViewModel(this)
        binding.viewModel = vm
        vm.mCallback = this
    }

    override fun onStarted() {
        val walletConnectKit = WalletConnectKit.Builder(config).build()
       // binding?.walletConnectButton?.sessionCallback = this@LoginActivity
        binding.walletConnectButton.start(walletConnectKit, ::onConnected, ::onDisconnected)
    }
    override fun onSuccess() {
        startNewActivity(MainActivity::class.java)
    }
    override fun onFailure(message: String) {
        myToast(message)
    }

    private fun onConnected(address: String) {
        println("You are connected with account: $address")
        if (isInternetAvailable()) {
            loginPostAPI(FirebaseToken.token, address, "0")
            Log.e("wallet_address", address)
        }
    }

    private fun onDisconnected() {
        println("Account disconnected!")
    }

    private fun loginPostAPI(deviceToken: String?, walletAddress: String, walletBalance: String){
        startAnim()
        viewModel.loginAPI(deviceToken, walletAddress, walletBalance)
        viewModel.loginLiveData.observe(this, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    // SAVE USERS LOGIN DETAILS
                    pref.saveBooleanData(Constants.APP_LOGIN_STATUS, true)
                    pref.saveStringData(Constants.ACCESS_TOKEN, it?.accessToken)

                    // ALL USER DATA STORED
                    val gson = Gson()
                    val json = gson.toJson(it?.oData)
                    pref.saveStringData(Constants.APP_USER_DATA, json)
                    callProfileData()
                    AppNavigator.navigateToHomeActivity(this@LoginActivity, true, moveTo, ids)
                    /*CustomDialog.showWelcomeDialog(this@LoginActivity,"",  object : DialogListener{
                        override fun setValue(value: String, position: Int) {
                            AppNavigator.navigateToHomeActivity(this@LoginActivity, true)
                        }
                    })*/
                }
            } else {
                myToast(it?.message!!)
            }
        })
    }

    override fun onMethodCall(call: Session.MethodCall) {
        myToast("Not Connect")
    }

    override fun onStatus(status: Session.Status) {
        myToast("Connect")
    }


}