package com.depromeet.linkzupzup.dataSources.repositories

import com.depromeet.linkzupzup.presenter.model.WeeklyAlarm

interface AlarmRepository {

    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm>

}