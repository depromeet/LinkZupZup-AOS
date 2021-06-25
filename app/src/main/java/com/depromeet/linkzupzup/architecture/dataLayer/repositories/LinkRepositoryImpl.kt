package com.depromeet.linkzupzup.architecture.dataLayer.repositories

import com.depromeet.linkzupzup.architecture.dataLayer.LinkDataSource
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.PersonalLinkEntity
import com.depromeet.linkzupzup.component.RoomDB
import io.reactivex.Observable

class LinkRepositoryImpl(private val roomDB: RoomDB, private val linkDataSource: LinkDataSource): LinkRepository {

    override fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>> {
        return linkDataSource.getLinkList(query)
    }

    override fun getLinkDetail(linkId: Int): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkDataSource.getLinkDetail(linkId)
    }

    override fun registerLink(linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkDataSource.insertLink(linkRegisterEntity)
    }

    override fun updateLink(linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkDataSource.updateLink(linkRegisterEntity = linkRegisterEntity)
    }

    override fun deleteLink(linkId: Int): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkDataSource.deleteLink(linkId = linkId)
    }

    override fun getTodayReadCount(): Observable<ResponseEntity<Int>> {
        return linkDataSource.getTodayReadCount()
    }

    override fun setLinkRead(linkId: Int): Observable<ResponseEntity<LinkReadEntity>> {
        return linkDataSource.setLinkRead(linkId)
    }

    override suspend fun getMetaList(urls: ArrayList<String>): List<LinkMetaInfoEntity> {
        return roomDB.metaDao().getMetaInfoList(urls)
    }

    override suspend fun insertMetaInfo(metaInfoEntity: LinkMetaInfoEntity) {
        roomDB.metaDao().insertMetaInfo(metaInfoEntity)
    }

    override suspend fun insertPersonalLinkAlarm(personalLinkEntity: PersonalLinkEntity): Long {
        return roomDB.metaDao().insertPersonalLinkAlarm(personalLinkEntity = personalLinkEntity)
    }

    override suspend fun getPersonalLinkAlarm(linkId: Int): PersonalLinkEntity? {
        return roomDB.metaDao().getPersonalLinkAlarm(linkId= linkId)
    }

    override suspend fun readComplete(linkId: Int) {
        return roomDB.metaDao().readComplete(linkId = linkId)
    }

}