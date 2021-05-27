package com.depromeet.linkzupzup.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.main.MainActivity
import java.util.*

/**
 * Notification Id : id가 동일한 푸시가 도착할 경우, Update되지만
 * id가 다를 경우, 별도의 Notification 이 할당됩니다.
 *
 * Notification Channel Id : channel에 따른 알림음, 진동등 Notification의 설정을 달리 관리할 수 있습니다.
 */
class AlarmReceiver: BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 3210123
    }

    var notificationManager: NotificationManager? = null

    /**
     * 푸시를 응답받는 곳입니다.
     * 이곳에서 ChannelId에 따라 Notification 설정을 달리 나눕니다.
     */
    override fun onReceive(context: Context?, data: Intent?) {
        val linkData = data?.getStringExtra("linkData") ?: "null"
        DLog.e("AlarmReceiver", "onReceive, linkData: $linkData")

        context?.let { ctx ->
            notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            createNotificationChannel()
            deliverNotification(ctx)
        }
    }

    /**
     * 알람 디자인에 맞춰 채널을 미리 정의해야됨.
     */
    fun createNotificationChannel(notifiItem: NotifiItem = NotifiItem("Title", "content")) = with(notifiItem) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                channel.channelId,
                channel.channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = channel.description
            }.let { notificationChannel ->
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    /**
     * 푸시 호출
     */
    private fun deliverNotification(context: Context,
                                    notifiItem: NotifiItem = NotifiItem("Title", "content"),
                                    contentIntent: Intent = Intent(context, MainActivity::class.java),
                                    notificationId: Int = Calendar.getInstance().timeInMillis.toInt(),
                                    intentFlag: Int = PendingIntent.FLAG_UPDATE_CURRENT) = with(notifiItem) {

        PendingIntent.getActivity(context,
            notificationId,
            contentIntent,
            intentFlag).let { contentPendingIntent ->

                NotificationCompat.Builder(context, channel.channelId)
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

}