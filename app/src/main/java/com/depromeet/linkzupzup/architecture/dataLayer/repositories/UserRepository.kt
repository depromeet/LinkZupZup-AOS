package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignUpEntity
import io.reactivex.Observable

interface UserRepository {

    fun signIn(signInEntity: SignInEntity): Observable<SignResponseEntity>

    fun signUp(signUpEntity: SignUpEntity): Observable<SignResponseEntity>

}