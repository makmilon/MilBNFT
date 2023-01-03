package com.mobiria.bnft.ui.fragment.notification

import android.view.View
import androidx.databinding.ObservableField
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.firebase.FirebaseUserHandler
import com.mobiria.bnft.firebase.NotificationModel
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_order.order_detail.OrderDetailsFragment
import com.mobiria.bnft.ui.fragment.notification.public_notification.PublicNotificationFragment
import com.mobiria.bnft.ui.fragment.product_details.DetailsFragment
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.DateFormatChanger
import com.mobiria.bnft.utils.dialog.CustomDialog

class NotificationItemViewModel(val mActivity: BaseActivity, val item: NotificationModel) : ViewModel {

    @JvmField
    val mNotificationModel = ObservableField<NotificationModel>()

    @JvmField
    val mTitle = ObservableField<String>()

    @JvmField
    val mDescription = ObservableField<String>()

    @JvmField
    val mDate = ObservableField<String>()

    init {
        mNotificationModel.set(item)
        with(item) {
            mTitle.set(getTitle())
            mDescription.set(getDescription())
            if (!getCreatedAt().isNullOrEmpty()) {
                val date: String? = DateFormatChanger.convertTimeAccordingTimeZone(
                    getCreatedAt()
                )
                mDate.set(date)
            }
        }
    }

    fun onClearNotificationClick(view: View) {
        CustomDialog.showLogoutDialog(
            mActivity,
            mActivity.resources?.getString(R.string.sure_delete_notif),
            object : DialogListener {
                override fun setValue(value: String, position: Int) {
                    try {
                        FirebaseUserHandler.deleteSingleNotification(
                            mActivity, mActivity.userLogin?.firebase_user_key!!,
                            item.getNotificationId()!!
                        )
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
    }

    fun onItemClick(view: View) {
        if (item.getNotificationType() == "public-notification") {
            val fragment = PublicNotificationFragment()
            fragment.newInstance(
               item.getTitle(),
                item.getDescription(),
                item.getImageURL()
            )
            mActivity.replaceFragment(fragment)
        } else if (item.getNotificationType() == "order_placed") {
            try {
                val fragment = OrderDetailsFragment()
                fragment.newInstance(item.getProduct_id()!!, "#" + item.getProduct_id())
                mActivity.replaceFragment(fragment)
            } catch (e: Exception){
                e.printStackTrace()
            }
        } else if (item.getNotificationType() == "bid_place") {
        } else if (item.getNotificationType() == "bid_end") {
        } else if (item.getNotificationType() == "bid_winner") {
        } else if (item.getNotificationType() == "resell_alert") {
            try {
                val fragment = DetailsFragment(true)
                fragment.newInstance(item.getProduct_id())
                mActivity.replaceFragment(fragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

        }
    }

    override fun close() {
    }
}