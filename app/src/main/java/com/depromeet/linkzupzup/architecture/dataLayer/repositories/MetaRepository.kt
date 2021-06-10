package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity

interface MetaRepository {

    fun getTagList(): ArrayList<String>

    suspend fun getMetaData(linkUrl: String): LinkMetaInfoEntity?

}