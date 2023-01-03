package com.mobiria.bnft.ui.fragment.account.edit_profile

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.auth.ODataLogin

class EditProfileViewModel(val mActivity: BaseActivity, userLogin: ODataLogin) : ViewModel() {

    @JvmField
    val mData = ObservableField<ODataLogin>()

    @JvmField
    val mProfileImage = ObservableField<String>()

    @JvmField
    val mFullName = ObservableField<String>()

    @JvmField
    val mobileNumber = ObservableField("")

    @JvmField
    val mDialCode = ObservableField<String>()

    @JvmField
    val mEmail = ObservableField("")

    init {
        mData.set(userLogin)
        with(userLogin) {
            mDialCode.set(dial_code)
            mFullName.set(display_name)
            mEmail.set(email)
            mobileNumber.set(mobile)
            mProfileImage.set(user_image)
        }
    }
}