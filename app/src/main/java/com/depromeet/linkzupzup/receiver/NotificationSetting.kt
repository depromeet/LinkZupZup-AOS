package com.depromeet.linkzupzup.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.view.main.MainActivity
import java.util.*


/**
 * Notification 채널 초기화
 * 개별 링크 알람과 통계 알람 두종류가 있습니다.
 */
enum class NotifiChannel(val channelId: String, val channelName: String, val description: String) {

    PERSONAL(
        "personal_notification_channel",
        "개별 링크 알람 채널",
        "개별 링크 알람 전용 채널입니다."
    ),

    STATISTICS(
        "statistics_notification_channel",
        "통계 알람 채널",
        "통계 알람 전용 채널입니다."
    )

}

object NotificationSetting {

    const val PERSONAL_ALROM_START = "com.depromeet.linkzupzup.PERSONAL_ALARM_START"
    const val STATISTICS_ALROM_START = "com.depromeet.linkzupzup.STATISTICS_ALARM_START"

    var notificationManager: NotificationManager? = null

    fun initChannelId(ctx: Context, managerblock: NotificationManager.()->Unit = {}) {
        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)
            ?.also { notificationManager = it }
            ?.run {

                // 개별 링크 알람 채널 생성
                createPersonalNotifiChannel()
                // 통계 알람 채널 생성
                createStatisticsNotifiChannel()

                managerblock()
            }
    }

    /**
     * 개별 링크 알람 채널 생성
     */
    private fun createPersonalNotifiChannel(channel: NotifiChannel = NotifiChannel.PERSONAL) = with(channel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
                description = description
            }.let { notificationChannel ->
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    /**
     * 통계 알람 채널 생성
     */
    private fun createStatisticsNotifiChannel(channel: NotifiChannel = NotifiChannel.STATISTICS) = with(channel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = description
            }.let { notificationChannel ->
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    /**
     * 푸시 호출
     */
    fun deliverNotification(context: Context,
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

/**
 * Notification 호출 시, 사용될 모델입니다.
 */
data class NotifiItem(

    val contentTitle: String,
    val contentText: String,

    val channel: NotifiChannel = NotifiChannel.PERSONAL,

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