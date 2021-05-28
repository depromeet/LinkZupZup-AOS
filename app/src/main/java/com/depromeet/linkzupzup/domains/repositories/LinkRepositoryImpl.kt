package com.depromeet.linkzupzup.domains.repositories

import android.content.Context
import com.depromeet.linkzupzup.dataSources.LinkDataSource
import com.depromeet.linkzupzup.dataSources.roomdb.RoomDB
import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import com.depromeet.linkzupzup.domains.entities.LinkEntity
import io.reactivex.Observable
import kotlinx.coroutines.runBlocking

class LinkRepositoryImpl(context : Context, private val linkDataSource: LinkDataSource): LinkRepository {

    private val roomDB = RoomDB.getInstance(context)
    private val linkDAO = roomDB.linkDao()

    override fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkDataSource.getLinkList(query)
    }

    override fun insertLink(linkEntity: LinkEntity) {
        runBlocking {
            linkDAO.insertLink(link = linkEntity)
        }
    }
}