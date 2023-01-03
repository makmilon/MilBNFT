package com.mobiria.bnft.utils.app_loader

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import com.mobiria.bnft.R

class CustomLoaderDialog {
    companion object {
        fun createProgressDialog(mContext: Context?, cancelable: Boolean): ProgressDialog? {
            val progressDialog = ProgressDialog(mContext)
            try {
                progressDialog.show()
            } catch (e: Exception) {
                Log.d("progress_error", e.message!!)
            }
            progressDialog.setCancelable(cancelable)
            progressDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.setContentView(R.layout.loader_dialog)
            return progressDialog
        }
    }
}