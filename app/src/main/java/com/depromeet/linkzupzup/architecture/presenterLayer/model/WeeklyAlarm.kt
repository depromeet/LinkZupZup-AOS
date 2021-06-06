package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.architecture.domainLayer.entities.RepeatedDate
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.getRepeatedDate
import com.depromeet.linkzupzup.extensions.getInt

data class WeeklyAlarm (
    var alarmId: Int = -1,
    var dateTime: String = "",
    var isWeekday: Int = 0, // 0: 평일, 1: 주말, 3: 매일
    var isHolidayUse: Int = 0,
    var enableAlarm: Int = 0) {

    fun isEnableAlarm(): Boolean = enableAlarm == 1
    fun isHolidayUse(): Boolean = isHolidayUse == 1
    fun weekDayStr() = when (isWeekday) {
        0 -> "#월화수목금"
        1 -> "토일"
        else -> "매일"
    }

    constructor(alarmEntity: AlarmEntity) : this() {
        this.alarmId = alarmEntity.alarmId
        this.dateTime = alarmEntity.notifyTime
        when(alarmEntity.repeatedDate.getRepeatedDate()) {

            // 매일
            RepeatedDate.EVERYDAY -> {
                isWeekday = 3
                isHolidayUse = 1
            }
            // 매일 & 공휴일 제외
            RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS -> {
                isWeekday = 3
                isHolidayUse = 1
            }
            // 평일
            RepeatedDate.WEEKDAYS -> {
                isWeekday = 0
                isHolidayUse = 1
            }
            // 평일 & 공휴일 제외
            RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS -> {
                isWeekday = 0
                isHolidayUse = 0
            }
            // 주말
            RepeatedDate.WEEKENDS -> {
                isWeekday = 1
                isHolidayUse = 1
            }
            // 주말 & 공휴일 제외
            RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS -> {
                isWeekday = 1
                isHolidayUse = 0
            }

            // 평일
            else -> {
                isWeekday = 0
                isHolidayUse = 1
            }
        }
        this.enableAlarm = alarmEntity.enabled.getInt()
    }
}