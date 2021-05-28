package com.depromeet.linkzupzup.domains

import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.domains.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.presenter.model.LinkData
import io.reactivex.Observable

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkRepositoryImpl.getLinkList(query)
    }

    // 링크를 저장하는 유즈케이스
    fun insertLinkInfo(link: LinkData) {

        val linkEntity : LinkEntity = link.mapToDataLayer()
        linkRepositoryImpl.insertLink(linkEntity)
    }

}