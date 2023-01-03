package com.mobiria.bnft.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.mobiria.bnft.ui.dashboard.MainActivity
import com.mobiria.bnft.utils.AndroidUtility
import com.mobiria.bnft.utils.app_loader.CustomLoaderDialog
import com.mobiria.bnft.utils.preference_data.PreferenceStore

open class BaseFragment : Fragment() {

    protected var TAG = "BNFT Application"
    protected lateinit var mActivity: BaseActivity
    internal lateinit var context: Context
    protected lateinit var mainActivity: MainActivity

    private var mProgressDialog: ProgressDialog? = null
    lateinit var pref: PreferenceStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = getActivity() as BaseActivity
        mainActivity = getActivity() as MainActivity

        mActivity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        TAG = javaClass.simpleName
        pref = PreferenceStore(mActivity)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    open fun startAnim() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = CustomLoaderDialog.createProgressDialog(context, false)
            } else {
                mProgressDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun stopAnim() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog?.dismiss()
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    open fun onBackPressed() {
        try {
         //   AndroidUtility().hideKeyboard(view)
            requireFragmentManager().popBackStack()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}