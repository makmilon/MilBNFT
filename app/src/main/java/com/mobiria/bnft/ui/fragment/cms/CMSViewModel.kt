package com.mobiria.bnft.ui.fragment.cms

import android.os.Build
import android.text.Html
import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.comman.ViewModel

class CMSViewModel(val mActivity: BaseActivity, val item: ODataCMS) : ViewModel {

   @JvmField
   val mCMS = ObservableField<ODataCMS>()

   @JvmField
   val mImage = ObservableField<String>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mMessage = ObservableField<String>()


    init {
        mCMS.set(item)
        with(item) {
            mTitle.set(title)
            mMessage.set(htmlToString(description))
        }
    }


    fun htmlToString(string: String?) : String {
        var text: String = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           text = Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            text = Html.fromHtml(string).toString()
        }
        return text
    }


    fun onItemClick(view: View) {

    }

    override fun close() {
    }
}