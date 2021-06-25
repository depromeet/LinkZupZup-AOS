package com.depromeet.linkzupzup.architecture.domainLayer

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmDataEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkReadEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.PersonalLinkEntity
import io.reactivex.Observable

class LinkUseCases(private val linkRepositoryImpl: LinkRepositoryImpl) {

    /**
     * 링크 리스트 조회
     */
    fun getLinkList(query: HashMap<String, Any>): Observable<ResponseEntity<LinkAlarmDataEntity>> {
        return linkRepositoryImpl.getLinkList(query)
    }

    /**
     * 링크 상세 조회
     */
    fun getLinkDetail(linkId: Int): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkRepositoryImpl.getLinkDetail(linkId)
    }


    /**
     * 링크 등록
     */
    fun registerLink(linkInfo: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>>{
        return linkRepositoryImpl.registerLink(linkInfo)
    }

    /**
     * 링크 수정
     */
    fun updateLink(linkRegisterEntity: LinkRegisterEntity): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkRepositoryImpl.updateLink(linkRegisterEntity = linkRegisterEntity)
    }

    /**
     * 링크 삭제
     */
    fun deleteLink(linkId: Int): Observable<ResponseEntity<LinkAlarmEntity>> {
        return linkRepositoryImpl.deleteLink(linkId = linkId)
    }

    /**
     * 오늘 읽은 링크 수 조회
     */
    fun getTodayReadCount(): Observable<ResponseEntity<Int>>{
        return linkRepositoryImpl.getTodayReadCount()
    }

    /**
     * 링크 읽기 상태로 변경
     */
    fun setLinkRead(linkId: Int): Observable<ResponseEntity<LinkReadEntity>>{
        return linkRepositoryImpl.setLinkRead(linkId)
    }

    suspend fun getMetaList(urls: ArrayList<String>): List<LinkMetaInfoEntity> {
        return linkRepositoryImpl.getMetaList(urls)
    }

    suspend fun insertMetaInfo(linkMetaInfoEntity: LinkMetaInfoEntity) {
        linkRepositoryImpl.insertMetaInfo(linkMetaInfoEntity)
    }

    suspend fun getPersonalLinkAlarm(linkId: Int): PersonalLinkEntity? {
        return linkRepositoryImpl.getPersonalLinkAlarm(linkId= linkId)
    }

    suspend fun readComplete(linkId: Int) {
        return linkRepositoryImpl.readComplete(linkId = linkId)
    }

}