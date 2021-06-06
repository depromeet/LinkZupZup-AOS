package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmUpdateEntity
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Path

interface AlarmRepository {

    /**
     * 어플 알람 리스트 조회
     */
    fun getAlarmList(): Observable<ResponseEntity<ArrayList<AlarmEntity>>>

    /**
     * 어플 알람 등록
     */
    fun registAlarm(alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmRegistEntity>>

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    fun getAlarmDetail(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<AlarmEntity>>

    /**
     * 특정 어플 알람 수정
     */
    fun updateAlarm(@Path("alarmId") alarmId: Int, @Body alarmInfo: AlarmUpdateEntity): Observable<ResponseEntity<AlarmEntity>>

    /**
     * 특정 어플 알람 삭제
     */
    fun deleteAlarm(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<String?>>

}