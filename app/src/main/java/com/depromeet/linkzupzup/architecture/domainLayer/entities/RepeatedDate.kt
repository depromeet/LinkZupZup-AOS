package com.depromeet.linkzupzup.architecture.domainLayer.entities

import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm

enum class RepeatedDate(var str: String) {

    // 매일
    EVERYDAY("EVERYDAY"),

    // 매일 & 공휴일 제외
    EVERYDAY_EXCEPT_HOLIDAYS("EVERYDAY_EXCEPT_HOLIDAYS"),

    // 평일
    WEEKDAYS("WEEKDAYS"),

    // 평일 & 공휴일 제외
    WEEKDAYS_EXCEPT_HOLIDAYS("WEEKDAYS_EXCEPT_HOLIDAYS"),

    // 주말
    WEEKENDS("WEEKENDS"),

    // 주말 & 공휴일 제외
    WEEKENDS_EXCEPT_HOLIDAYS("WEEKENDS_EXCEPT_HOLIDAYS")

}

fun String.getRepeatedDate(): RepeatedDate? = when(this) {
    RepeatedDate.EVERYDAY.str -> RepeatedDate.EVERYDAY
    RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS.str -> RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS
    RepeatedDate.WEEKDAYS.str -> RepeatedDate.WEEKDAYS
    RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS.str -> RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS
    RepeatedDate.WEEKENDS.str -> RepeatedDate.WEEKENDS
    RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS.str -> RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS
    else -> null
}

fun RepeatedDate.isWeekday(): Int = when {
    // 매일
    equals(RepeatedDate.EVERYDAY) || equals(RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS) -> WeeklyAlarm.EVERYDAY
    // 평일
    equals(RepeatedDate.WEEKDAYS) || equals(RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS) -> WeeklyAlarm.WEEKDAYS
    // 주말
    equals(RepeatedDate.WEEKENDS) || equals(RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS) -> WeeklyAlarm.WEEKENDS
    // 평일
    else -> WeeklyAlarm.EMPTY
}

fun RepeatedDate.isHoliday(): Int = when {
    // 매일
    equals(RepeatedDate.EVERYDAY) -> 1
    // 매일 & 공휴일 제외
    equals(RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS) -> 0
    // 평일
    equals(RepeatedDate.WEEKDAYS) -> 1
    // 평일 & 공휴일 제외
    equals(RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS) -> 0
    // 주말
    equals(RepeatedDate.WEEKENDS) -> 1
    // 주말 & 공휴일 제외
    equals(RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS) -> 0
    // 평일
    else -> 1
}

/**
 * 주중, repeatValues[0]
 * 주말, repeatValues[1]
 * 공휴일 알람 여부
 */
fun getReatDateStr(repeatValues: ArrayList<Int>, isHoliday: Int): String = when {

    // 매일
    (repeatValues[0] == 1 && repeatValues[1] == 1 && isHoliday == 1) -> RepeatedDate.EVERYDAY.str

    // 매일 & 공휴일 제외
    (repeatValues[0] == 1 && repeatValues[1] == 1 && isHoliday == 0) -> RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS.str

    // 평일
    (repeatValues[0] == 1 && repeatValues[1] == 0 && isHoliday == 1) -> RepeatedDate.WEEKDAYS.str

    // 평일 & 공휴일 제외
    (repeatValues[0] == 1 && repeatValues[1] == 0 && isHoliday == 0) -> RepeatedDate.WEEKDAYS_EXCEPT_HOLIDAYS.str

    // 주말
    (repeatValues[0] == 0 && repeatValues[1] == 1 && isHoliday == 1) -> RepeatedDate.WEEKENDS.str

    // 주말 & 공휴일 제외
    (repeatValues[0] == 0 && repeatValues[1] == 1 && isHoliday == 0) -> RepeatedDate.WEEKENDS_EXCEPT_HOLIDAYS.str

    // 평일
    else -> RepeatedDate.WEEKDAYS.str

}