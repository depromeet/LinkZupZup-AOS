package com.depromeet.linkzupzup.extensions

import com.depromeet.linkzupzup.utils.DateUtil
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