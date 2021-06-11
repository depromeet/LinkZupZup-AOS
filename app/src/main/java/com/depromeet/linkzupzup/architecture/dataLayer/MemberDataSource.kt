package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.architecture.dataLayer.api.MemberAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import io.reactivex.Observable

class MemberDataSource(private val api: MemberAPIService) {

    fun signInUp(signInUpEntity: SignInUpEntity): Observable<ResponseEntity<SignResponseEntity>>
        = api.signInUp(signInUpEntity = signInUpEntity)

    fun logout(loginId: Long): Observable<ResponseEntity<String?>>
        = api.logout(loginId = loginId)

    fun getMyPageInfo(): Observable<ResponseEntity<MyPageInfoResponseEntity>>
        = api.getMyPageInfo()

    fun setAlarmEnabled(alarmEnabled: String): Observable<ResponseEntity<MyPageInfoResponseEntity>>
        = api.setAlarmEnabled(alarmEnabled = alarmEnabled)

}