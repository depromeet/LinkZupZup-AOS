package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import io.reactivex.Observable

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {

    fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>> {
        return linkRepositoryImpl.getLinkList(query)
    }

    suspend fun getMetaList(urls: ArrayList<String>): List<LinkMetaInfoEntity> {
        return linkRepositoryImpl.getMetaList(urls)
    }

    suspend fun insertMetaInfo(linkMetaInfoEntity: LinkMetaInfoEntity) {
        linkRepositoryImpl.insertMetaInfo(linkMetaInfoEntity)
    }

}