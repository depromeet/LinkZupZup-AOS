package com.depromeet.linkzupzup.architecture.presenterLayer

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.architecture.domainLayer.AlarmUseCases
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm

class AlarmDetailViewModel(private val alarmUseCases: AlarmUseCases): BaseViewModel() {

    companion object {
        val TAG = AlarmDetailViewModel::class.java.simpleName
    }

    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm> {
        return alarmUseCases.getWeeklyAlarmList()
    }

}