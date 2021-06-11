package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import io.reactivex.Observable

interface UserRepository {

    fun signInUp(signInEntity: SignInUpEntity): Observable<ResponseEntity<SignResponseEntity>>

    fun getMyPageInfo(): Observable<ResponseEntity<MyPageInfoEntity>>

    fun setAlarmEnabled(alarmEnabled: String): Observable<ResponseEntity<MyPageInfoEntity>>

}