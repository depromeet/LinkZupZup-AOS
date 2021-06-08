package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.getRepeatedDate
import com.depromeet.linkzupzup.architecture.domainLayer.entities.isHoliday
import com.depromeet.linkzupzup.architecture.domainLayer.entities.isWeekday
import com.depromeet.linkzupzup.extensions.getInt

data class WeeklyAlarm (
    var alarmId: Int = -1,
    var dateTime: String = "",
    var isWeekday: Int = -1, // 0: 평일, 1: 주말, 2: 매일
    var isHolidayUse: Int = 0,
    var enableAlarm: Int = 0) {

    companion object {
        const val EMPTY = -1
        const val WEEKDAYS = 0
        const val WEEKENDS = 1
        const val EVERYDAY = 2
        fun WeeklyAlarm.clone(): WeeklyAlarm = WeeklyAlarm(alarmId, dateTime, isWeekday, isHolidayUse, enableAlarm)
    }

    fun isEnableAlarm(): Boolean = enableAlarm == 1
    fun isHolidayUse(): Boolean = isHolidayUse == 1
    fun weekDayStr() = when (isWeekday) {
        WEEKDAYS -> "#월화수목금"
        WEEKENDS -> "토일"
        EVERYDAY -> "매일"
        else -> ""
    }
    // weekday: 평일
    // weekend: 주말
    fun setWeekday(weekday: Int, weekend: Int) {
        isWeekday = when {
            weekday == 0 && weekend == 0 -> EMPTY
            weekday == 1 && weekend == 0 -> WEEKDAYS
            weekday == 0 && weekend == 1 -> WEEKENDS
            else -> EVERYDAY
        }
    }

    constructor(alarmEntity: AlarmEntity): this() {
        this.alarmId = alarmEntity.alarmId
        this.dateTime = alarmEntity.notifyTime
        this.isWeekday = alarmEntity.repeatedDate.getRepeatedDate()?.isWeekday() ?: 0
        this.isHolidayUse = alarmEntity.repeatedDate.getRepeatedDate()?.isHoliday() ?: 1
        this.enableAlarm = alarmEntity.enabled.getInt()
    }
}