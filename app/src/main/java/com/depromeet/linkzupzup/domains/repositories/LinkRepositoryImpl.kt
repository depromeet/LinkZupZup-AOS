package com.depromeet.linkzupzup.domains.repositories

import com.depromeet.linkzupzup.dataSources.LinkDataSource
import com.depromeet.linkzupzup.dataSources.roomdb.RoomDB
import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import io.reactivex.Observable

class LinkRepositoryImpl(private val roomDB: RoomDB, private val linkDataSource: LinkDataSource): LinkRepository {

    override fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkDataSource.getLinkList(query)
    }

}