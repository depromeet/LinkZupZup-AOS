package com.depromeet.linkzupzup.dataSources.repositories

import com.depromeet.linkzupzup.domains.entities.LinkEntity
import com.depromeet.linkzupzup.dataSources.roomdb.RoomDB
import kotlinx.coroutines.runBlocking

class LinkRepositoryImpl(roomDB: RoomDB) : LinkRepository {

    private val linkDAO = roomDB.linkDao()

    override fun insertLink(linkEntity: LinkEntity) {
        runBlocking {
            linkDAO.insertLink(link = linkEntity)
        }
    }


}