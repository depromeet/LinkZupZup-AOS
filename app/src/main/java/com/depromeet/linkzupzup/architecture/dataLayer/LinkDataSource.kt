package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.architecture.dataLayer.api.LinkAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmResponseEntity
import io.reactivex.Observable

class LinkDataSource(private val api: LinkAPIService) {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity>
        = api.getLinkList(query)

}