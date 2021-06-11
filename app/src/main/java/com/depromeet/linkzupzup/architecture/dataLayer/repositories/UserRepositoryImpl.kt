package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.MemberDataSource
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.DonutHistoryEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignInUpEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.SignResponseEntity
import io.reactivex.Observable

class UserRepositoryImpl(private var memberDataSource: MemberDataSource): UserRepository {

    override fun signInUp(signInEntity: SignInUpEntity): Observable<ResponseEntity<SignResponseEntity>> {
        return memberDataSource.signInUp(signInEntity)
    }

    override fun getMyPageInfo(): Observable<ResponseEntity<MyPageInfoEntity>> {
        return memberDataSource.getMyPageInfo()
    }

    override fun setAlarmEnabled(alarmEnabled: String): Observable<ResponseEntity<MyPageInfoEntity>> {
        return memberDataSource.setAlarmEnabled(alarmEnabled = alarmEnabled)
    }

    override fun getDonutHistory(query: HashMap<String, Any>): Observable<ResponseEntity<DonutHistoryEntity>> {
        return memberDataSource.getDonutHistoryList(query)
    }

}