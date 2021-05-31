package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm

interface AlarmRepository {

    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm>

}