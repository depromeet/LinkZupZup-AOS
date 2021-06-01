package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseArrayEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Path

class AlarmUseCases(private val alarmRepositoryImpl: AlarmRepositoryImpl) {

    // 사용자 정보를 가져오는 UseCase
    fun getWeeklyAlarmList(): ArrayList<WeeklyAlarm> {
        /**
         * 이구간에서 Presenter -> Domain으로만 의존성이 향하게 처리하기 위해
         * Entity를 Model로 변환하는 과정을 거쳐야 합니다.
         */
        return alarmRepositoryImpl.getWeeklyAlarmList()
    }

    /**
     * 어플 알람 리스트 조회
     */
    fun getAlarmList(): Observable<ResponseArrayEntity<AlarmEntity>>
        = alarmRepositoryImpl.getAlarmList()

    /**
     * 어플 알람 등록
     */
    fun registAlarm(alarmInfo: String): Observable<ResponseEntity<AlarmRegistEntity>>
        = alarmRepositoryImpl.registAlarm(alarmInfo)

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    fun getAlarmDetail(alarmId: Int): Observable<ResponseEntity<AlarmEntity>>
        = alarmRepositoryImpl.getAlarmDetail(alarmId)

    /**
     * 특정 어플 알람 수정
     */
    fun updateAlarm(alarmId: Int, alarmInfo: String): Observable<ResponseEntity<AlarmEntity>>
        = alarmRepositoryImpl.updateAlarm(alarmId, alarmInfo)

    /**
     * 특정 어플 알람 삭제
     */
    fun deleteAlarm(alarmId: Int): Observable<ResponseEntity<String?>>
        = alarmRepositoryImpl.deleteAlarm(alarmId)

}