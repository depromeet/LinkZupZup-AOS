package com.depromeet.linkzupzup.dataSources

import com.depromeet.linkzupzup.dataSources.api.LinkAPIService
import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import io.reactivex.Observable

class LinkDataSource(private val api: LinkAPIService) {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity>
        = api.getLinkList(query)

}