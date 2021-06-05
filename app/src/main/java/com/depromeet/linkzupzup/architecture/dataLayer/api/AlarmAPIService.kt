package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmRegistEntity
import io.reactivex.Observable
import retrofit2.http.*

interface AlarmAPIService {

    /**
     * 어플 알람 리스트 조회
     */
    @GET(ApiUrl.ALARM_LIST)
    fun getAlarmList(): Observable<ResponseEntity<ArrayList<AlarmEntity>>>

    /**
     * 어플 알람 등록
     */
    @POST(ApiUrl.ALARM_REGIST)
    fun registAlarm(alarmInfo: String): Observable<ResponseEntity<AlarmRegistEntity>>

    /**
     * 특정 어플 알람의 세부 내용 조회
     */
    @GET("${ApiUrl.ALARM_DETAIL}/{alarmId}")
    fun getAlarmDetail(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<AlarmEntity>>

    /**
     * 특정 어플 알람 수정
     */
    @PUT("${ApiUrl.ALARM_UPDATE}/{alarmId}")
    fun updateAlarm(@Path("alarmId") alarmId: Int, @Body alarmInfo: String): Observable<ResponseEntity<AlarmEntity>>

    /**
     * 특정 어플 알람 삭제
     */
    @DELETE("${ApiUrl.ALARM_DELETE}/{alarmId}")
    fun deleteAlarm(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<String?>>

}