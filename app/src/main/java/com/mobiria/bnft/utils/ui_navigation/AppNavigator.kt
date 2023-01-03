package com.mobiria.bnft.utils.ui_navigation

import android.content.Context
import android.content.Intent
import com.mobiria.bnft.ui.auth.LoginActivity
import com.mobiria.bnft.ui.dashboard.MainActivity
import com.mobiria.bnft.utils.Constants

object AppNavigator {

    fun navigateToHomeActivity(context: Context, isClear: Boolean, moveTo: String? = "", ids: String?= "") {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(Constants.KEY_MOVE_TO, moveTo)
        intent.putExtra(Constants.KEY_PRODUCT_ID, ids)
        if (isClear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

    fun navigateToLoginActivity(context: Context, isClear: Boolean, moveTo: String = "", ids: String= "") {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(Constants.KEY_MOVE_TO, moveTo)
        intent.putExtra(Constants.KEY_PRODUCT_ID, ids)
        if (isClear) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

}