package com.depromeet.linkzupzup.extensions

import com.depromeet.linkzupzup.utils.DateUtil
import java.text.DecimalFormat
import java.util.*

/**
 * 서버에서 내려오는 datatime의 경우 hh:mm 만 내려오는경우가 있습니다. 이럴경우를 대비하여 아래와 같이 확장메서드를 추가합니다.
 */
fun calendarInstance(): Calendar = Calendar.getInstance()
fun String.getCalendar(): Calendar {
    return if (isNotEmpty() && contains("^\\d{2}:\\d{2}")) {
        val times = split(":")
        val hour = times[0].toInt()
        val minute = times[1].toInt()
        Calendar.getInstance().clearMillis().apply {
           set(Calendar.HOUR, hour)
           set(Calendar.MINUTE, minute)
        }
    } else calendarInstance()
}
fun Calendar.hour(): Int = get(Calendar.HOUR)
fun Calendar.minute(): Int = get(Calendar.MINUTE)
fun Calendar.hourStr(): String = hour().toString()
fun Calendar.minuteStr(): String = minute().toString()
fun Calendar.isAm(): Boolean = get(Calendar.AM_PM) == Calendar.AM
fun Calendar.timeBaseStr(): String = if (isAm()) "오전" else "오후"
fun Calendar.timeStr(digitFormat: String = "%02d"): String
    = arrayOf(
    hour().digitFormat(digitFormat),
    minute().digitFormat(digitFormat)
).joinToString(":")


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
            cal.get(Calendar.MINUTE).digitFormat()
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

fun Calendar.clearMillis(): Calendar = apply {
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
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

/**
 * AlarmMananger에 알람을 등록할 시, 사용될 requestCode를 아래와 같이
 * "년월일시분초"를 모두 더한 합으로 사용할 예정입니다.
 */
fun Calendar.getTotalTimeSum(): Int
    = get(Calendar.YEAR) +
      get(Calendar.MONTH) +
      get(Calendar.DATE) +
      get(Calendar.HOUR) +
      get(Calendar.MINUTE) +
      get(Calendar.SECOND)