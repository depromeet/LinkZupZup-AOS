package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.UserEntity
import io.reactivex.Single

interface UserRepository {

    fun getUserInfo(): Single<UserEntity>

    fun updateMyProfileUserInfo(user: UserEntity): Single<UserEntity>

}