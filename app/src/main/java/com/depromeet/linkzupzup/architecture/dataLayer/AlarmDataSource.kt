package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.ParamsInfo
import com.depromeet.linkzupzup.architecture.dataLayer.api.AlarmAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmUpdateEntity
import io.reactivex.Observable

class AlarmDataSource(private val api: AlarmAPIService) {

    /**
     * 어플 알람 리스트 조회
     */
    fun getAlarmList(): Observable<ResponseEntity<ArrayList<AlarmEntity>>>
        = api.getAlarmList()

    /**
     * 어플 알람 등록
     */
    fun registAlarm(alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmEntity>>
        = api.registAlarm(alarmInfo)

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    fun getAlarmDetail(alarmId: Int): Observable<ResponseEntity<AlarmEntity>>
        = api.getAlarmDetail(alarmId)

    /**
     * 특정 어플 알람 수정
     */
    fun updateAlarm(alarmId: Int, alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmEntity>>
        = api.updateAlarm(alarmId, alarmInfo)

    /**
     * 특정 어플 알람 삭제
     */
    fun deleteAlarm(alarmId: Int): Observable<ResponseEntity<String?>>
        = api.deleteAlarm(alarmId)
    fun deleteAlarm(params: HashMap<String, Any?>): Observable<ResponseEntity<String?>> = with(params) {
        val alarmId = params[ParamsInfo.ALARM_ID] as? Int ?: 0
        deleteAlarm(alarmId)
    }

}