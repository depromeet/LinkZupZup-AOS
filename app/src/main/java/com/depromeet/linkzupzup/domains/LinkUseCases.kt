package com.depromeet.linkzupzup.domains

import com.depromeet.linkzupzup.dataSources.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.domains.entities.LinkEntity
import com.depromeet.linkzupzup.extensions.mapToDataLayer
import com.depromeet.linkzupzup.presenter.model.LinkData

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {
    // 링크를 저장하는 유즈케이스
    fun insertLinkInfo(link: LinkData) {

        val linkEntity : LinkEntity = link.mapToDataLayer()
        linkRepositoryImpl.insertLink(linkEntity)
    }
}