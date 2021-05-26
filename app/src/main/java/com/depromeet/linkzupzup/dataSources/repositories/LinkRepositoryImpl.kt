package com.depromeet.linkzupzup.dataSources.repositories

import android.content.Context
import com.depromeet.linkzupzup.domains.entities.LinkEntity
import com.depromeet.linkzupzup.roomdb.RoomDB
import kotlinx.coroutines.runBlocking

class LinkRepositoryImpl(context : Context) : LinkRepository {

    private val roomDB = RoomDB.getInstance(context)
    private val linkDAO = roomDB.linkDao()

    override fun insertLink(linkEntity: LinkEntity) {
        runBlocking {
            linkDAO.insertLink(link = linkEntity)
        }
    }


}