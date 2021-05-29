package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmResponseEntity
import io.reactivex.Observable

interface LinkRepository {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity>

}