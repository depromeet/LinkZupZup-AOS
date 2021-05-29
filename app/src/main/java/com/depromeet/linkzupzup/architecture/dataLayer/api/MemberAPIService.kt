package com.depromeet.linkzupzup.architecture.dataLayer.api

import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignUpEntity
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
    fun signIn(@Body signIn: SignInEntity): Observable<SignResponseEntity>

    @POST(ApiUrl.MEMBERS_SIGN_UP)
    fun signUp(@Body signUp: SignUpEntity): Observable<SignResponseEntity>

}