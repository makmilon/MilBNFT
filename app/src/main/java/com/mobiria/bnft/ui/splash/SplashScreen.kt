package com.mobiria.bnft.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.databinding.ActivitySplashBinding
import com.mobiria.bnft.ui.auth.LoginActivity
import com.mobiria.bnft.ui.dashboard.MainActivity
import com.mobiria.bnft.utils.Constants

class SplashScreen : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var vm: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        init()
    }
    private fun init(){
        vm = SplashViewModel(this)
        binding?.viewModel = vm
        vm.callAnimation(binding?.appImage!!)
        callToNextActivity()
    }

    fun callToNextActivity(){
        Handler().postDelayed(Runnable {
            var deepLinkData = ""
            if (intent.data !=null){
                deepLinkData = Gson().toJson(intent.data.toString())
            }
            if (pref.getBooleanData(Constants.APP_LOGIN_STATUS)!!) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("deepLinkData", deepLinkData)
                startActivity(intent)
                finish()
                finishAffinity()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                finishAffinity()
            }
        }, Constants.SPLASH_DISPLAY_LENGTH)
    }
}