package com.depromeet.linkzupzup.architecture.domainLayer.entities

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