package com.depromeet.linkzupzup.component

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.view.main.MainActivity

class AlarmReceiver: BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    var notificationManager: NotificationManager? = null

    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let { ctx ->
            notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            createNotificationChannel()
            deliverNotification(ctx)
        }

    }

    /**
     * 알람 디자인에 맞춰 채널을 미리 정의해야됨.
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "AlarmManager Tests"
            }.let { notificationChannel ->
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun deliverNotification(context: Context,
                                    notifiItem: NotifiItem = NotifiItem("Title", "content"),
                                    contentIntent: Intent = Intent(context, MainActivity::class.java),
                                    notificationId: Int = NOTIFICATION_ID,
                                    intentFlag: Int = PendingIntent.FLAG_UPDATE_CURRENT) = with(notifiItem) {
        PendingIntent.getActivity(context,
            notificationId,
            contentIntent,
            intentFlag).let { contentPendingIntent ->

                NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(smallIcon)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(priority)
                    .setAutoCancel(isAutoCancel)
                    .setDefaults(effectType).let { builder ->
                        notificationManager?.notify(notificationId, builder.build())
                }

        }
    }




    data class NotifiItem(

        val contentTitle: String,
        val contentText: String,

        val channelId: String = PRIMARY_CHANNEL_ID,
        @DrawableRes
        val smallIcon: Int = R.mipmap.ic_launcher,
        val priority: Int = NotificationCompat.PRIORITY_HIGH,
        val isAutoCancel: Boolean = true,

        /**
         * ## https://developer.android.com/reference/android/app/Notification#DEFAULT_ALL ##
         *
         * NotificationCompat.COLOR_DEFAULT     : color이 알림을 특별한 색상으로 장식하지 않고 대신이 알림을 표시 할 때 기본 색상을 사용하도록 시스템 에 알리는 특별한 값입니다 .
         * NotificationCompat.DEFAULT_ALL       : 모든 기본값 (소리, 진동)을 사용합니다 (해당되는 경우).
         * NotificationCompat.DEFAULT_LIGHTS    : 기본 알림 표시등을 사용합니다.
         * NotificationCompat.DEFAULT_SOUND     : 기본 알림 소리를 사용합니다.
         * NotificationCompat.DEFAULT_VIBRATE   : 기본 알림 진동을 사용합니다.
         */
        val effectType: Int = NotificationCompat.DEFAULT_ALL

    )
}