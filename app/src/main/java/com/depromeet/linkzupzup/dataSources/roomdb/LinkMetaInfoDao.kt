package com.depromeet.linkzupzup.dataSources.roomdb

import androidx.room.*
import com.depromeet.linkzupzup.domains.entities.LinkMetaInfoEntity

@Dao
interface LinkMetaInfoDao {
    // suspend = coroutine 을 이용해 background 에서 동작함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetaInfo(meta: LinkMetaInfoEntity): Long

    @Query("SELECT * FROM LinkMetaInfo WHERE meta_url =:url")
    suspend fun getMetaInfo(url: String) : LinkMetaInfoEntity?

    @Query("SELECT * FROM LinkMetaInfo")
    suspend fun getMetaInfoList() : List<LinkMetaInfoEntity>
}