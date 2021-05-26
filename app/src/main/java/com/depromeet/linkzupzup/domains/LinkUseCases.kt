package com.depromeet.linkzupzup.domains

import com.depromeet.linkzupzup.dataSources.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.extensions.mapToPresenter
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.roomdb.LinkVO
import io.reactivex.Single

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {
    // 링크를 저장하는 유즈케이스
    fun insertLinkInfo(linkVO: LinkVO) {
        /**
         * 이구간에서 Presenter -> Domain으로만 의존성이 향하게 처리하기 위해
         * Entity를 Model로 변환하는 과정을 거쳐야 합니다.
         */
        linkRepositoryImpl.insertLink(linkVO = linkVO)
    }
}