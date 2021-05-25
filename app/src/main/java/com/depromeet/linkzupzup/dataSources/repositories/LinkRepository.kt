package com.depromeet.linkzupzup.dataSources.repositories

import com.depromeet.linkzupzup.roomdb.LinkVO

interface LinkRepository {

    fun insertLink(linkVO: LinkVO)

}