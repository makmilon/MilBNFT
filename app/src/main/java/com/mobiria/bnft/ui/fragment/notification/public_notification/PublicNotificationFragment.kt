package com.mobiria.bnft.ui.fragment.notification.public_notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentPublicNotificationBinding

class PublicNotificationFragment : BaseFragment() {

    private lateinit var mBinding: FragmentPublicNotificationBinding
    private lateinit var mVm: PublicNotificationViewModel

    private var image: String? = ""
    private var title: String? = ""
    private var message: String? = ""

    fun newInstance(title: String?, message: String?, imageUrl: String?): PublicNotificationFragment {
        this.image = imageUrl
        this.title = title
        this.message = message
        return PublicNotificationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_notification,
            container, false)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.public_notifications), false, false)

        mVm = ViewModelProvider(this)[PublicNotificationViewModel::class.java]
        mBinding.viewModel = mVm
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVm.imageUrl.set(image)
        mVm.title.set(title)
        mVm.message.set(message)
        if (!image.isNullOrEmpty()) {
            mVm.isImage.set(true)
        }
    }

}