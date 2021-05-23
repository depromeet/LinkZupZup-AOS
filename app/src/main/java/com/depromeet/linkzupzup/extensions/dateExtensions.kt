package com.depromeet.linkzupzup.extensions

import com.depromeet.linkzupzup.utils.DateUtil
import java.text.DecimalFormat
import java.util.*

/**
 * 0 : 오전, 1: 오후
 */
fun String.isAm(): Boolean
    = DateUtil.dateStrToCalendar(this).get(Calendar.AM_PM) == 0
fun String.timeBaseStr(): String
    = if (isAm()) "오전" else "오후"
fun String.timeStr(digitFormat: String = "%02d"): String
    = DateUtil.dateStrToCalendar(this).let { cal ->
        arrayOf(
            cal.get(Calendar.HOUR).digitFormat(),
            cal.get(Calendar.HOUR).digitFormat()
        ).joinToString(":")
    }
fun String.hour(): Int
    = DateUtil.dateStrToCalendar(this).get(Calendar.HOUR)
fun String.minute(): Int
    = DateUtil.dateStrToCalendar(this).get(Calendar.MINUTE)
// 숫자의 자릿수를 digitFormat에 맞춰 문자열로 반환
fun Int.digitFormat(digitFormat: String = "%02d"): String
    = String.format(digitFormat, this)

// 숫자의 1000단위 마다 반점을 넣은 문자열로 반환
fun Int.digitFormat1000(digitFormat: DecimalFormat = DecimalFormat("#,###,###")): String
    = digitFormat.format(this)

fun Calendar.getDay(): Int = get(Calendar.DATE)

fun Calendar.compareDate(calendar: Calendar): Boolean {
    return get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
           get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
           get(Calendar.DATE) == calendar.get(Calendar.DATE)

}

fun Calendar.isToday(): Boolean = compareDate(Calendar.getInstance())

fun Calendar.getAlarmDateStr(): String {
    val hour = getDay()
    val dateStr = "4월 10일 오전 12:50분"
    val dateLastStr = "에 알림이 울려요!"
    val alarmDateStr = "${dateStr}${dateLastStr}"
    return ""
}


/**
 * Long Time Value -> Calendar
 */
fun timeToCalendar(timeValue: Long): Calendar
        = Calendar.getInstance().apply { time = Date(timeValue) }
// fun calendarToTIme()

/**
 * Calendar -> AM or PM
 * 0: AM (= Calendar.AM )
 * 1: PM (= Calendar.PM )
 */
fun Calendar.isAmPm(): Boolean = getAMPM() == Calendar.AM
/**
 * 0: AM (= Calendar.AM )
 * 1: PM (= Calendar.PM )
 */
fun Calendar.getAMPM(): Int = get(Calendar.AM_PM)
fun Calendar.setAMorPM(amOrPm: Int): Calendar = apply { set(Calendar.AM_PM, amOrPm) }

/**
 * Calendar -> Month
 */
fun Calendar.getMonth(): Int = get(Calendar.MONTH)
fun Calendar.setMonth(month: Int): Calendar = apply { set(Calendar.MONTH, month) }

/**
 * Calendar -> Date
 */
fun Calendar.getDate(): Int = get(Calendar.DATE)
fun Calendar.setDate(year: Int, month: Int, day: Int): Calendar = apply {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month)
    set(Calendar.DATE, day)
}
fun Calendar.setDate(date: Calendar): Calendar = apply {
    set(Calendar.YEAR, date.get(Calendar.YEAR))
    set(Calendar.MONTH, date.get(Calendar.MONTH))
    set(Calendar.DATE, date.get(Calendar.DATE))
}

/**
 * Calendar -> hour
 */
fun Calendar.getHourValue(): Int = get(Calendar.HOUR)
fun Calendar.setHour(hour: Int): Calendar = apply { set(Calendar.HOUR, hour) }

/**
 * Minute
 */
fun Calendar.getMinuteValue(): Int = get(Calendar.MINUTE)
fun Calendar.setMinute(minute: Int): Calendar = apply { set(Calendar.MINUTE, minute) }