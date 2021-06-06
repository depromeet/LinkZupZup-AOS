package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmUpdateEntity
import io.reactivex.Observable

class AlarmUseCases(private val alarmRepositoryImpl: AlarmRepositoryImpl) {

    /**
     * 어플 알람 리스트 조회
     */
    fun getAlarmList(): Observable<ResponseEntity<ArrayList<AlarmEntity>>>
        = alarmRepositoryImpl.getAlarmList()

    /**
     * 어플 알람 등록
     */
    fun registAlarm(alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmRegistEntity>>
        = alarmRepositoryImpl.registAlarm(alarmInfo)

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    fun getAlarmDetail(alarmId: Int): Observable<ResponseEntity<AlarmEntity>>
        = alarmRepositoryImpl.getAlarmDetail(alarmId)

    /**
     * 특정 어플 알람 수정
     */
    fun updateAlarm(alarmId: Int, alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmEntity>>
        = alarmRepositoryImpl.updateAlarm(alarmId, alarmInfo)

    /**
     * 특정 어플 알람 삭제
     */
    fun deleteAlarm(alarmId: Int): Observable<ResponseEntity<String?>>
        = alarmRepositoryImpl.deleteAlarm(alarmId)

}