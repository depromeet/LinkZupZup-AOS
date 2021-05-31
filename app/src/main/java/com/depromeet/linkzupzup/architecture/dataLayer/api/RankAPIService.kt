package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import io.reactivex.Observable
import retrofit2.http.*

interface RankAPIService {

//    /**
//     * 어플 알람 리스트 조회
//     */
//    @GET(ApiUrl.ALARM_LIST)
//    fun getAlarmList(): Observable<ResponseEntity<String>>
//
//    /**
//     * 어플 알람 등록
//     */
//    @POST(ApiUrl.ALARM_REGIST)
//    fun registAlarm(alarmInfo: String): Observable<ResponseEntity<String>>
//
//    /**
//     * 특정 어플 알람의 세부 내용 조회
//     */
//    @GET("${ApiUrl.ALARM_DETAIL}/{alarmId}")
//    fun getAlarmDetail(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<String>>
//
//    /**
//     * 특정 어플 알람 수정
//     */
//    @PUT("${ApiUrl.ALARM_UPDATE}/{alarmId}")
//    fun updateAlarm(@Path("alarmId") alarmId: Int, @Body alarmInfo: String): Observable<ResponseEntity<String>>
//
//    @DELETE("${ApiUrl.ALARM_DELETE}/{alarmId}")
//    fun deleteAlarm(@Path("alarmId") alarmId: Int): Observable<ResponseEntity<String>>

}