package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.AlarmDataSource
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm
import io.reactivex.Observable

class AlarmRepositoryImpl(private val alarmDataSource: AlarmDataSource): AlarmRepository {

    override fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm> {
        // dataSource (API or Database) 를 참조하여 데이터를 반환합니다.
        return arrayListOf<WeeklyAlarm>().apply {
            add(WeeklyAlarm("2021-04-28 08:30:00", 0, 1, 1))
            add(WeeklyAlarm("2021-04-28 08:30:00", 1, 1, 0))
            add(WeeklyAlarm("2021-04-28 08:30:00", 2, 0, 1))
        }
    }


    /**
     * 어플 알람 리스트 조회
     */
    override fun getAlarmList(): Observable<ResponseEntity<AlarmEntity>>
        = alarmDataSource.getAlarmList()

    /**
     * 어플 알람 등록
     */
    override fun registAlarm(alarmInfo: String): Observable<ResponseEntity<AlarmRegistEntity>>
        = alarmDataSource.registAlarm(alarmInfo)

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    override fun getAlarmDetail(alarmId: Int): Observable<ResponseEntity<AlarmEntity>>
        = alarmDataSource.getAlarmDetail(alarmId)

    /**
     * 특정 어플 알람 수정
     */
    override fun updateAlarm(alarmId: Int, alarmInfo: String): Observable<ResponseEntity<AlarmEntity>>
        = alarmDataSource.updateAlarm(alarmId, alarmInfo)

    /**
     * 특정 어플 알람 삭제
     */
    override fun deleteAlarm(alarmId: Int): Observable<ResponseEntity<String?>>
        = alarmDataSource.deleteAlarm(alarmId)

}