package com.depromeet.linkzupzup.architecture.dataLayer.dao

import androidx.room.*
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.PersonalLinkEntity

@Dao
interface LinkMetaInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetaInfo(meta: LinkMetaInfoEntity): Long

    @Query("SELECT * FROM LinkMetaInfo WHERE meta_url = :url")
    suspend fun getMetaInfo(url: String): LinkMetaInfoEntity?

    @Query("SELECT * FROM LinkMetaInfo WHERE meta_url IN(:urls)")
    suspend fun getMetaInfoList(urls: ArrayList<String>) : List<LinkMetaInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalLinkAlarm(personalLinkEntity: PersonalLinkEntity): Long

    @Query("SELECT * FROM PersonalLink WHERE link_id = :linkId")
    suspend fun getPersonalLinkAlarm(linkId: Int): PersonalLinkEntity?

    @Query("UPDATE PersonalLink SET link_completed = 'Y', link_alarm_enable = 'N' WHERE link_id = :linkId")
    fun readComplete(linkId: Int)

}