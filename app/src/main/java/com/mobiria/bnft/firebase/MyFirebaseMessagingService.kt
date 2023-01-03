package com.mobiria.bnft.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.mobiria.bnft.R
import com.mobiria.bnft.ui.dashboard.MainActivity
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    var title: String? = ""
    var message: String? = ""
    var type: String? = ""
    var imageURL: String? = ""
    var click_action: String? = null
    var mDataJson: String? = null

    // ONLY FOR CHAT
    var order_id: String? = null
    var notificationID: String? = null


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        try {
            if (remoteMessage.notification != null) {
                title = remoteMessage.notification!!.title
                message = remoteMessage.notification!!.body
                Log.e("RemoteNotification  : ", remoteMessage.data.toString())

                click_action = remoteMessage.notification!!.clickAction
                try {
                    mDataJson = Gson().toJson(remoteMessage.data)
                    type = remoteMessage.data["type"].toString()
                    order_id = remoteMessage.data["product_id"].toString()
                    imageURL = remoteMessage.data["imageURL"].toString()
                    notificationID = remoteMessage.data["notificationID"]

                    if (message.isNullOrEmpty()) {
                        message = remoteMessage.data["body"].toString()
                    }
                  /*  val myIntent = Intent("ORDER")
                    myIntent.putExtra("type", type)
                    myIntent.putExtra("order_id", order_id)
                    (DataFactory.baseActivity)?.sendBroadcast(myIntent)*/

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                sendNotification(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun sendNotification(messageBody: String?) {
        try {
            val notificationId = Random().nextInt(60000)


            var intent: Intent? = null
            intent?.action = "order_notification"
            intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("moveToNext", "notification")
            intent.putExtra("order_id", order_id)
            intent.putExtra("type", type)
            intent.putExtra("title", title)
            intent.putExtra("message", message)
            intent.putExtra("image", imageURL)
            intent.putExtra("notificationID", notificationID)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

           // Log.e("notification", " firebase : "+title+" "+messageBody+ " "+ProductLink+" "+createdAt)
            var pendingIntent: PendingIntent? = null
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
            } else {
                pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }



            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val d = getDrawable(R.mipmap.ic_launcher)
                val bitmap = (d as BitmapDrawable?)!!.bitmap
                val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //.setBadgeIconType(R.mipmap.ic_app_launcher_round)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
            //            .setLargeIcon(bitmap)
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText(messageBody)
                        )
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(
                    notificationId /*ID of notification_layout*/,
                    notificationBuilder.build()
                )
            } else {
                setNotificationForOrio(
                    title!!,
                    messageBody,
                    notificationId.toString() + "",
                    pendingIntent!!
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun setNotificationForOrio(
        type: String,
        messageBody: String?,
        channelId: String,
        pendingIntent: PendingIntent
    ) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = Notification.Builder(
            applicationContext
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(channelId, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(notificationManager != null)
            notificationBuilder.setChannelId(channelId)
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            notificationBuilder.setContentIntent(pendingIntent)
            notificationBuilder.setBadgeIconType(R.mipmap.ic_launcher)
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setContentTitle(type)
            notificationBuilder.setContentText(messageBody)
            notificationBuilder.style = Notification.BigTextStyle().bigText(messageBody)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationManager.notify(
                Random().nextInt(60000) /*ID of notification_layout*/,
                notificationBuilder.build()
            )
        }
    }
}