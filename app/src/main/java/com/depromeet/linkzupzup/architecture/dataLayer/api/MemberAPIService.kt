package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

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

}