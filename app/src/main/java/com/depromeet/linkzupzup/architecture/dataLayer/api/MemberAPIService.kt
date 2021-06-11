package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.*
import io.reactivex.Observable
import retrofit2.http.*

interface MemberAPIService {

    /**
     * Parameters
     *
     * completed=F
     * pageNumber=0
     * pageSize=1
     */
    @POST(ApiUrl.MEMBERS_SIGN_IN)
    fun signInUp(@Body signInUpEntity: SignInUpEntity): Observable<ResponseEntity<SignResponseEntity>>

    @PUT(ApiUrl.MEMBERS_LOGOUT)
    fun logout(@Header("loginId") loginId: Long): Observable<ResponseEntity<String?>>

    @GET(ApiUrl.MEMBERS_MYPAGE_INFO)
    fun getMyPageInfo(): Observable<ResponseEntity<MyPageInfoEntity>>

    @FormUrlEncoded
    @POST(ApiUrl.MEMBER_ALARM_ENABLED)
    fun setAlarmEnabled(@Field("alarmEnabled") alarmEnabled: String): Observable<ResponseEntity<MyPageInfoEntity>>

    @GET(ApiUrl.MEMBER_DONUT_HISTORY)
    fun getDonutHistoryList(@QueryMap query: HashMap<String, Any>): Observable<ResponseEntity<DonutHistoryEntity>>

}