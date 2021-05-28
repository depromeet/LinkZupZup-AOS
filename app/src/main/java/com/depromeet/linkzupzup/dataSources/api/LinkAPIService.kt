package com.depromeet.linkzupzup.dataSources.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

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
    fun getLinkList(@QueryMap query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity>

}