package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.*
import io.reactivex.Observable
import retrofit2.http.*

interface LinkAPIService {

//    restful url path 데이터를 넣는 경우
//    @POST("${ApiUrl.LINK_LIST}/{id}")
//    fun getLinkList(@Path("id") linkId: Int): Observable<LinkAlarmResponseEntity>

    /**
     * Parameters
     *
     * completed=F
     * pageNumber=0
     * pageSize=1
     */
    @GET(ApiUrl.LINK_LIST)
    fun getLinkList(@QueryMap query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>>

    @POST(ApiUrl.LINK_LIST)
    fun registerLink(@Body linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>>

    @GET(ApiUrl.LINK_COUNT)
    fun getTodayReadCount(): Observable<ResponseEntity<Int>>

    @PATCH(ApiUrl.LINK_LIST+"/{id}")
    fun setLinkRead(@Path("id") linkId: Int): Observable<ResponseEntity<LinkReadEntity>>

}