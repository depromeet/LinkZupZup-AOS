package com.depromeet.linkzupzup.dataSources.repositories

import com.depromeet.linkzupzup.domains.entities.UserEntity
import io.reactivex.Single

class UserRepositoryImpl: UserRepository {

    override fun getUserInfo(): Single<UserEntity> {
        // dataSource (API or Database) 를 참조하여 데이터를 반환합니다.
        return Single.just(UserEntity(0, "JACKSON01", 31))
    }

    override fun updateMyProfileUserInfo(user: UserEntity): Single<UserEntity> {
        return Single.just(user)
    }

}