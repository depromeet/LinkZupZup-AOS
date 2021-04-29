package com.depromeet.linkzupzup.component

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.activity.ComponentActivity
import java.util.*

/**
 * DI ( Koin ) 을 사용할 예정
 * 참고 : https://codechacha.com/ko/android-alarmmanager/
 */
class LinkZupAlarmManager {

    lateinit var alarmManager: AlarmManager
    lateinit var alarmIntent: Intent
    lateinit var pendingIntent: PendingIntent

    fun getInstance(ctx: Context): LinkZupAlarmManager {
        alarmManager = ctx.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(ctx, AlarmReceiver::class.java)
        pendingIntent = createAlarmPendingIntent(ctx, AlarmReceiver.NOTIFICATION_ID,
            alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return this
    }

    fun createAlarmPendingIntent(ctx: Context,
                                 requestId: Int = AlarmReceiver.NOTIFICATION_ID,
                                 intent: Intent,
                                 flag: Int = PendingIntent.FLAG_UPDATE_CURRENT): PendingIntent
            = PendingIntent.getBroadcast(ctx, requestId, intent, flag).also { pendingIntent = it }

    /**
     * [ AlarmManager - type ]
     * ELAPSED_REALTIME : 기기가 부팅된 후 경과한 시간을 기준으로,
     * 상대적인 시간을 사용하여 알람을 발생시킵니다.
     * 기기가 절전모드(doze)에 있을 때는 알람을 발생시키지 않고 해제되면 발생시킵니다.
     *
     * ELAPSED_REALTIME_WAKEUP : ELAPSED_REALTIME와 동일하지만 절전모드일 때 알람을 발생시킵니다.
     * RTC : Real Time Clock을 사용하여 알람을 발생시킵니다. 절전모드일 때는 알람을 발생시키지 않습니다.
     * RTC_WAKEUP : RTC와 동일하지만 절전모드일 때 알람을 발생시킵니다.
     */
    /**
     * [ 현재 시간 및 경과 시간을 가져오는 API ]
     * System.currentTimeMillis() : 현재 시각을 UTC(1970년 1월 1일이 0인 시간)의 millisecond로 리턴합니다.
     * 디바이스에 설정된 현재 시각을 기준으로 리턴하기 때문에, 네트워크가 연결되어 시각이 변경되거나
     * 위도(Time zone)가 변경되어 UTC가 변경될 수 있습니다.
     *
     * SystemClock.elapsedRealtime() : 부팅된 시점부터 현재까지의 시간을 millisecond로 리턴합니다.
     * 즉, 부팅 직후에는 0을 리턴하고 10초가 지났다면 10000이 리턴됩니다.
     * 중요한 것은 디바이스가 Sleep 상태에 있어도 시간은 측정이 됩니다.
     * 만약 부팅된지 10초가 지났고, 이 중에 5초가 Sleep 상태였어도 API는 10초를 리턴합니다.
     * 따라서, 시간 간격(Interval)을 측정할 때는 이 API를 사용하면 좋습니다.
     *
     * SystemClock.elapsedRealtimeNanos() : elapsedRealtimeNanos()는 elapsedRealtime()와
     * 동일하게 동작하지만, nano seconds를 리턴합니다.
     *
     * SystemClock.uptimeMillis() : elapsedRealtime()와 마찬가지로 부팅된 시점부터 현재까지의 시간을
     * millisecond로 리턴합니다. 중요한 것은 디바이스가 Sleep 상태에 있을 때는 시간을 측정하지 않습니다.
     * 즉, 부팅한지 10분이 되었고, 5분 동안 Sleep 상태였다면 5분에 대한 시간만 millisecond로 리턴합니다.
     */

    /**
     * SDK API 19 미만에서는 정확히 설정한 시간에 알람이 발생되지만,
     * API 19이상에서는 덜정확한 시간에 알람이 발생됩니다.
     */
    fun setAlarm(triggerTime: Long = 60 * 1000,
                 operation: PendingIntent = pendingIntent,
                 type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {
        alarmManager.set(type,
            (SystemClock.elapsedRealtime() + triggerTime),
            operation)
    }

    /**
     * 정확한 시간에 알람이 발생
     * setExact()로 등록된 알람은 [ Doze모드 ]에서 호출되지 않음.
     */
    fun setExactAlarm(triggerTime: Long = 60 * 1000,
                      operation: PendingIntent = pendingIntent,
                      type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {
        alarmManager.setExact(
            type,
            (SystemClock.elapsedRealtime() + triggerTime),
            operation)
    }

    /**
     * set()과 동일하게 덜정확한 시간에 알람이 발생되지만
     * 절전모드에서도 동작하는 API입니다.
     */
    fun setAndAllowWhileIdle(triggerTime: Long = 60 * 1000,
                             operation: PendingIntent = pendingIntent,
                             type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {
        alarmManager.setAndAllowWhileIdle(
            type,
            (SystemClock.elapsedRealtime() + triggerTime),
            operation)
    }

    /**
     * setExact()과 동일하게 정확한 시간에 알람이 발생되지만
     * 절전모드에서도 동작하는 API입니다.
     */
    fun setExactAndAllowWhileIdle(triggerTime: Long = 60 * 1000,
                                  operation: PendingIntent = pendingIntent,
                                  type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {
        alarmManager.setExactAndAllowWhileIdle(
            type,
            (SystemClock.elapsedRealtime() + triggerTime),
            operation)
    }

    /**
     * set()과 동일하게 정확한 시간에 울리진 않지만
     * 대체적으로 비슷한 시간에 알람이 발생되며
     * 반복적으로 알람을 받도록 구현 가능
     * [ repeatInterval ] : 설정시간이 제한적
     * INTERVAL_DAY             : 1일
     * INTERVAL_FIFTEEN_MINUTES : 15분
     * INTERVAL_HALF_DAY        : 12시간
     * INTERVAL_HALF_HOUR       : 30분
     * INTERVAL_HOUR            : 1시간
     */
    fun setInexactRepeating(triggerTime: Long = 60 * 1000,
                            repeatInterval: Long = AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                            operation: PendingIntent = pendingIntent,
                            type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {

        alarmManager.setInexactRepeating(
            type,
            (SystemClock.elapsedRealtime() + triggerTime),
            repeatInterval,
            operation)
    }

    /**
     * setExact()과 동일하게 정한한 시간에 알람이 발생되며
     * 반복적으로 알람을 받도록 구현 가능
     * API 19 이상에서는 정확한 시간에 알람이 발생하는 것을 보장하지만,
     * 배터리 등의 문제로 API 19 이상에서는 정확한 시간에 알람이 발생되는 것을 보장하지 않습니다.
     * [ repeatInterval ] : 설정시간에 제한이 없음
     * setRepeating()은 setInexactRepeating()과 다르게 repeatInterval의 제한 없이 원하는 시간을 설정할 수 있습니다.
     *
     * ## 참고 : AlarmManager는 반복 알람을 등록할 때, 정확한 시간을 보장하는 API를 제공하지 않고 있습니다.
     */
    fun setRepeating(triggerTime: Long = 60 * 1000,
                     repeatInterval: Long = 15 * 60 * 1000,
                     operation: PendingIntent = pendingIntent,
                     type: Int = AlarmManager.ELAPSED_REALTIME_WAKEUP) {

        alarmManager.setRepeating(
            type,
            (SystemClock.elapsedRealtime() + triggerTime),
            repeatInterval,
            operation)
    }

    /**
     * Real Time으로 알람등록
     *
     * hourOfDay and minute 시간에 알람이 발생되고,
     * repeatInterval 시간 뒤에 한번더 알람이 발생
     */
    fun setRealTimeRepeating(hourOfDay: Int,
                             minute: Int,
                             repeatInterval: Long = 15 * 60 * 1000,
                             operation: PendingIntent = pendingIntent,
                             type: Int = AlarmManager.RTC_WAKEUP) {

        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }.let { calendar ->
            alarmManager.setRepeating(type, calendar.timeInMillis, repeatInterval, operation)
        }
    }



    fun removeAlarm(pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)
    }



}