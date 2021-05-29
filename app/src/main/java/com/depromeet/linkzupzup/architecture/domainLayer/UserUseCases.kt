package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.extensions.mapToPresenter
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.UserRepositoryImpl
import com.depromeet.linkzupzup.architecture.presenterLayer.model.User
import io.reactivex.Single

class UserUseCases(private val userRepositoryImpl: UserRepositoryImpl) {

    // 사용자 정보를 가져오는 UseCase
    fun getUserInfo(): Single<User> {
        /**
         * 이구간에서 Presenter -> Domain으로만 의존성이 향하게 처리하기 위해
         * Entity를 Model로 변환하는 과정을 거쳐야 합니다.
         */
        return userRepositoryImpl.getUserInfo().map { it.mapToPresenter() }
    }

}