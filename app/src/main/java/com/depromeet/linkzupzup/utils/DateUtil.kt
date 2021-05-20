package com.depromeet.linkzupzup.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val DEFAULT_DATE_FORMAT_STR: String = "yyyy-MM-dd HH:mm:ss"
    const val DATE_AM_PM_FORMAT_STR: String = "a"

    fun getSimpleDateFormat(pattenr: String = DEFAULT_DATE_FORMAT_STR): SimpleDateFormat
        = SimpleDateFormat(pattenr)

    /**
     * AM or PM
     */
    fun dateToAmPmStr(date: Date): String
        = getSimpleDateFormat(DATE_AM_PM_FORMAT_STR).format(date)
    fun dateStrToDate(dateStr: String): Date
            = getSimpleDateFormat().parse(dateStr)
    fun dateToCalendar(date: Date): Calendar
         = Calendar.getInstance().apply { time = date }
    fun dateStrToCalendar(dateStr: String): Calendar
            = dateToCalendar(dateStrToDate(dateStr))

    val weeklyStrArr = arrayListOf("일", "월", "화", "수", "목", "금", "토")

    fun getDateList(untilNum: Int = 37): ArrayList<Pair<String, Calendar>>
        = arrayListOf<Pair<String, Calendar>>().apply {
            (0..untilNum).forEach { inc ->
                Calendar.getInstance().let { cal ->
                    // increase
                    cal.add(Calendar.DATE, inc)

                    // day of week
                    val weekNum = cal.get(Calendar.DAY_OF_WEEK)
                    val weekStr = weeklyStrArr[weekNum - 1]

                    add(Pair(weekStr, cal.clone() as Calendar))
                }
            }
        }

}