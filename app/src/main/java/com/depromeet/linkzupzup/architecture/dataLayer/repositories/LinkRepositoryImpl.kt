package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.LinkDataSource
import com.depromeet.linkzupzup.component.RoomDB
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmResponseEntity
import io.reactivex.Observable

class LinkRepositoryImpl(private val roomDB: RoomDB, private val linkDataSource: LinkDataSource): LinkRepository {

    override fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity> {
        return linkDataSource.getLinkList(query)
    }

}