package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.MetaRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity

class MetaUseCases(private val metaRepositoryImpl: MetaRepositoryImpl) {

    // 사용자 정보를 가져오는 UseCase
    fun getTagList(scrapId: Int): ArrayList<String> {
        return metaRepositoryImpl.getTagList()
    }

    suspend fun getMetaData(linkUrl: String): LinkMetaInfoEntity? {
        return metaRepositoryImpl.getMetaData(linkUrl = linkUrl)
    }

}