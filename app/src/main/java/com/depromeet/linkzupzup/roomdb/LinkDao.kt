package com.depromeet.linkzupzup.roomdb

import androidx.room.*
import com.depromeet.linkzupzup.domains.entities.LinkEntity

@Dao
interface LinkDao {
    // suspend = coroutine 을 이용해 background 에서 동작함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLink(link: LinkEntity): Long

    @Query("SELECT * FROM Link WHERE url =:url")
    suspend fun getLink(url: String) : LinkEntity?

    @Query("SELECT * FROM Link")
    suspend fun getLinkList() : List<LinkEntity>
}