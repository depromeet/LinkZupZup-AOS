package com.depromeet.linkzupzup.presenter.model

data class WeeklyAlarm (
    var dateTime: String = "",
    var isWeekday: Int = 0, // 0: 주중, 1: 주말, 3: 매일
    var isHolidayUse: Int = 0,
    var enableAlarm: Int = 0) {

    fun isEnableAlarm(): Boolean = enableAlarm == 1
    fun isHolidayUse(): Boolean = isHolidayUse == 1
    fun weekDayStr() = when (isWeekday) {
        0 -> "#월화수목금"
        1 -> "토일"
        else -> "매일"
    }
}