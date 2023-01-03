package com.mobiria.bnft.ui.fragment.cms.contact_us

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.auth.ODataLogin

class ContactUsViewModel(val mActivity: BaseActivity, val item: ODataLogin) : ViewModel() {

    @JvmField
    val mLogin = ObservableField<ODataLogin>()

    @JvmField
    val mFullName = ObservableField<String>()

    @JvmField
    val mobileNumber = ObservableField("")

    @JvmField
    val mEmail = ObservableField("")

    init {
        mLogin.set(item)
        with(item) {
            mFullName.set(display_name)
            mobileNumber.set(mobile)
            mEmail.set(email)
        }
    }
}