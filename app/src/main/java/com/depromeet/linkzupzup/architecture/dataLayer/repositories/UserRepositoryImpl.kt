package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.MemberDataSource
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignUpEntity
import io.reactivex.Observable

class UserRepositoryImpl(private var memberDataSource: MemberDataSource): UserRepository {

    override fun signIn(signInEntity: SignInEntity): Observable<SignResponseEntity> {
        return memberDataSource.signIn(signInEntity)
    }

    override fun signUp(signUpEntity: SignUpEntity): Observable<SignResponseEntity> {
        return memberDataSource.signUp(signUpEntity)
    }

}