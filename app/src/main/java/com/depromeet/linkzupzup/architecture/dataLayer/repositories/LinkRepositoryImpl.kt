package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.LinkDataSource
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.component.RoomDB
import io.reactivex.Observable

class LinkRepositoryImpl(private val roomDB: RoomDB, private val linkDataSource: LinkDataSource): LinkRepository {

    override fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>> {
        return linkDataSource.getLinkList(query)
    }

}