package com.depromeet.linkzupzup.domains.repositories

import com.depromeet.linkzupzup.domains.entities.LinkAlarmResponseEntity
import io.reactivex.Observable

interface LinkRepository {

    fun getLinkList(query: HashMap<String, Any>): Observable<LinkAlarmResponseEntity>

}