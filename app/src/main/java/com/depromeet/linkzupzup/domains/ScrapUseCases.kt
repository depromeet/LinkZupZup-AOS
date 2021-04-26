package com.depromeet.linkzupzup.domains

import com.depromeet.linkzupzup.dataSources.repositories.TagRepositoryImpl

class ScrapUseCases(private val tagRepositoryImpl: TagRepositoryImpl) {

    // 사용자 정보를 가져오는 UseCase
    fun getTagList(scrapId: Int): ArrayList<String> {
        return tagRepositoryImpl.getTagList()
    }

}