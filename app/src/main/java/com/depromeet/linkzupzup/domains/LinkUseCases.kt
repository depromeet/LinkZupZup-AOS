package com.depromeet.linkzupzup.domains

import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.domains.repositories.LinkRepositoryImpl
import io.reactivex.Observable

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkRepositoryImpl.getLinkList(query)
    }

}