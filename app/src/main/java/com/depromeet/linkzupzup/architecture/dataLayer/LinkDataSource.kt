package com.depromeet.linkzupzup.architecture.dataLayer

import com.depromeet.linkzupzup.architecture.dataLayer.api.LinkAPIService
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import io.reactivex.Observable

class LinkDataSource(private val api: LinkAPIService) {

    fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>>
        = api.getLinkList(query)

    fun insertLink(linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>>
        = api.registerLink(linkRegisterEntity)

    fun getTodayReadCount(): Observable<ResponseEntity<Int>>
        = api.getTodayReadCount()

    fun setLinkRead(linkId: Int): Observable<ResponseEntity<LinkReadResponseEntity>>
        = api.setLinkRead(linkId)

}