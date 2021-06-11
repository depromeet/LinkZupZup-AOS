package com.depromeet.linkzupzup.architecture.dataLayer.dao

import androidx.room.*
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity

@Dao
interface LinkMetaInfoDao {
    // suspend = coroutine 을 이용해 background 에서 동작함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetaInfo(meta: LinkMetaInfoEntity): Long

    @Query("SELECT * FROM LinkMetaInfo WHERE meta_url = :url")
    suspend fun getMetaInfo(url: String): LinkMetaInfoEntity?

//    @Query("SELECT * FROM LinkMetaInfo")
//    suspend fun getAllMetaInfoList() : List<LinkMetaInfoEntity>

    @Query("SELECT * FROM LinkMetaInfo WHERE meta_url IN(:urls)")
    suspend fun getMetaInfoList(urls: ArrayList<String>) : List<LinkMetaInfoEntity>
}