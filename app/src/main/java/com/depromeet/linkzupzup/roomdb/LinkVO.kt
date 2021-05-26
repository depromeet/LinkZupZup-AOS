package com.depromeet.linkzupzup.roomdb

import androidx.room.*

@Entity(tableName = "Link")
class LinkVO {
    @PrimaryKey
    @ColumnInfo(name="url")
    var url : String = ""

    @ColumnInfo(name="title")
    var title : String = ""

    constructor()
    constructor(url : String, title : String){
        this.url = url
        this.title = title
    }
}

@Dao
interface LinkDAO {
    // suspend = coroutine을 이용해 background에서 동작함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertLink(linkVO: LinkVO): Long

    @Query("SELECT * FROM Link WHERE url =:url")
    suspend fun getLink(url: String) : LinkVO?

    @Query("SELECT * FROM Link")
    suspend fun getLinkList() : List<LinkVO>
}