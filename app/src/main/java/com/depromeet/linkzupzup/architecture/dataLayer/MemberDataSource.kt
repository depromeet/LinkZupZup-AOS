package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.architecture.dataLayer.api.MemberAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.DonutHistoryEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import io.reactivex.Observable

class MemberDataSource(private val api: MemberAPIService) {

    fun signInUp(signInUpEntity: SignInUpEntity): Observable<ResponseEntity<SignResponseEntity>>
        = api.signInUp(signInUpEntity = signInUpEntity)

    fun getMyPageInfo(): Observable<ResponseEntity<MyPageInfoEntity>>
        = api.getMyPageInfo()

    fun logout(loginId: Long): Observable<ResponseEntity<String?>>
        = api.logout(loginId = loginId)


    fun setAlarmEnabled(alarmEnabled: String): Observable<ResponseEntity<MyPageInfoEntity>>
        = api.setAlarmEnabled(alarmEnabled = alarmEnabled)

    fun getDonutHistoryList(query: HashMap<String, Any>): Observable<ResponseEntity<DonutHistoryEntity>>
        = api.getDonutHistoryList(query)

}