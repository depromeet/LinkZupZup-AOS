package com.depromeet.linkzupzup.dataSources.repositories

import com.depromeet.linkzupzup.domains.entities.LinkEntity

interface LinkRepository {

    fun insertLink(linkEntity: LinkEntity)

}