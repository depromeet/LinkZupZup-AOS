package com.depromeet.linkzupzup.dataSources.repositories

import android.content.Context
import com.depromeet.linkzupzup.roomdb.LinkVO
import com.depromeet.linkzupzup.roomdb.RoomDB
import kotlinx.coroutines.runBlocking

class LinkRepositoryImpl(context : Context) : LinkRepository {

    private val roomDB = RoomDB.getInstance(context)
    private val linkDAO = roomDB.linkDAO()

    override fun insertLink(linkVO: LinkVO) {
        runBlocking {
            linkDAO.insertLink(linkVO = linkVO)
        }
    }


}