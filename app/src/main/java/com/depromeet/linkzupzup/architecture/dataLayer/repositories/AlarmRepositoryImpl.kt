package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm

class AlarmRepositoryImpl: AlarmRepository {

    override fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm> {
        // dataSource (API or Database) 를 참조하여 데이터를 반환합니다.
        return arrayListOf<WeeklyAlarm>().apply {
            add(WeeklyAlarm("2021-04-28 08:30:00", 0, 1, 1))
            add(WeeklyAlarm("2021-04-28 08:30:00", 1, 1, 0))
            add(WeeklyAlarm("2021-04-28 08:30:00", 2, 0, 1))
        }
    }

}