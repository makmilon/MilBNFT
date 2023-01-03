package com.mobiria.bnft.ui.fragment.notification.public_notification

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class PublicNotificationViewModel : ViewModel() {

    @JvmField
    val imageUrl = ObservableField<String>()

    @JvmField
    val isImage = ObservableField<Boolean>()

    @JvmField
    val title = ObservableField<String>()

    @JvmField
    val message = ObservableField<String>()

    init {
        isImage.set(false)
    }
}