package com.depromeet.linkzupzup.repositories

import com.depromeet.linkzupzup.domains.entities.UserEntity
import io.reactivex.Single

interface UserRepository {

    fun getUserInfo(): Single<UserEntity>

    fun updateMyProfileUserInfo(user: UserEntity): Single<UserEntity>

}