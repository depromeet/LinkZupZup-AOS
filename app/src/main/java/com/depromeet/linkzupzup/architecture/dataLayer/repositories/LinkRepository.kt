package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import io.reactivex.Observable

interface LinkRepository {

    fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>>

    fun getLinkDetail(linkId: Int): Observable<ResponseEntity<LinkAlarmEntity>>

    fun registerLink(linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>>

    fun getTodayReadCount(): Observable<ResponseEntity<Int>>

    fun setLinkRead(linkId: Int): Observable<ResponseEntity<LinkReadResponseEntity>>

    suspend fun getMetaList(urls: ArrayList<String>) : List<LinkMetaInfoEntity>

    suspend fun insertMetaInfo(metaInfoEntity: LinkMetaInfoEntity)

}