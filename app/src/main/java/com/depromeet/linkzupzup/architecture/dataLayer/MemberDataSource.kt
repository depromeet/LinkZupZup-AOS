package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.architecture.dataLayer.api.MemberAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignUpEntity
import io.reactivex.Observable

class MemberDataSource(private val api: MemberAPIService) {

    fun signIn(signInEntity: SignInEntity): Observable<SignResponseEntity>
        = api.signIn(signIn = signInEntity)

    fun signUp(signUpEntity: SignUpEntity): Observable<SignResponseEntity>
        = api.signUp(signUpEntity)

}