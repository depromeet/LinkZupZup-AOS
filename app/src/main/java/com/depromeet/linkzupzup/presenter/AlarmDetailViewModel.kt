package com.depromeet.linkzupzup.presenter

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.AlarmUseCases
import com.depromeet.linkzupzup.presenter.model.WeeklyAlarm

class AlarmDetailViewModel(private val alarmUseCases: AlarmUseCases): BaseViewModel() {

    companion object {
        val TAG = AlarmDetailViewModel::class.java.simpleName
    }

    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm> {
        return alarmUseCases.getWeeklyAlarmList()
    }

}