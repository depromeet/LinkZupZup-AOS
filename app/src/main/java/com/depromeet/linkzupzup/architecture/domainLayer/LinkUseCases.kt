package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.LinkRepositoryImpl
import io.reactivex.Observable

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkRepositoryImpl.getLinkList(query)
    }


}