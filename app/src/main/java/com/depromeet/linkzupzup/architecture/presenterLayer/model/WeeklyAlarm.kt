package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.architecture.domainLayer.entities.*
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity

data class WeeklyAlarm (
    var alarmId: Int = -1,
    var dateTime: String = "",
    var weeklydayend: Int = 0, // 0: 평일, 1: 주말, 2: 매일
    var disableHoliday: Int = 0,
    var enableAlarm: Boolean = false) {

    companion object {
        const val WEEKDAYS = 0
        const val WEEKENDS = 1
        const val EVERYDAY = 2
        fun WeeklyAlarm.clone(): WeeklyAlarm = WeeklyAlarm(alarmId, dateTime, weeklydayend, disableHoliday, enableAlarm)
    }

    fun weekDayStr() = when (weeklydayend) {
        WEEKDAYS -> "#월화수목금"
        WEEKENDS -> "토일"
        EVERYDAY -> "매일"
        else -> "#월화수목금"
    }
    fun getRepeatedDate(): String
        = getReatDateStr(repeatValues = arrayListOf(getWeeklyday(), getWeeklyend()), disableHoliday = disableHoliday)

    constructor(alarmEntity: AlarmEntity): this() {
        this.alarmId = alarmEntity.alarmId
        this.dateTime = alarmEntity.notifyTime
        this.weeklydayend = alarmEntity.repeatedDate.getRepeatedDate().isWeekday()
        this.disableHoliday = alarmEntity.repeatedDate.getRepeatedDate().disableHoliday()
        this.enableAlarm = alarmEntity.enabled
    }
}

fun WeeklyAlarm.getWeeklyday(): Int {
    return weeklydayend.let { weeklyday ->
        when(weeklyday) {
            WeeklyAlarm.WEEKDAYS -> 1
            WeeklyAlarm.WEEKENDS -> 0
            WeeklyAlarm.EVERYDAY -> 1
            else -> 0
        }
    }
}

fun WeeklyAlarm.getWeeklyend(): Int {
    return weeklydayend.let { weeklyend ->
        when(weeklyend) {
            WeeklyAlarm.WEEKDAYS -> 0
            WeeklyAlarm.WEEKENDS -> 1
            WeeklyAlarm.EVERYDAY -> 1
            else -> 0
        }
    }
}

fun getWeeklydayend(weeklyday: Int, weeklyend: Int): Int = when {
    // 매일
    (weeklyday == 1 && weeklyend == 1) -> WeeklyAlarm.EVERYDAY
    // 평일
    (weeklyday == 1 && weeklyend == 0) -> WeeklyAlarm.WEEKDAYS
    // 주말
    (weeklyday == 0 && weeklyend == 1) -> WeeklyAlarm.WEEKENDS
    // 평일
    else -> WeeklyAlarm.WEEKDAYS
}