package com.depromeet.linkzupzup.domains.repositories

import com.depromeet.linkzupzup.presenter.model.WeeklyAlarm

interface AlarmRepository {

    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm>

}