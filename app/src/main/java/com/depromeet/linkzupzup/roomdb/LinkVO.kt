package com.depromeet.linkzupzup.roomdb

import androidx.room.*

@Entity(tableName = "Link")
class LinkVO() {

    @PrimaryKey
    @ColumnInfo(name="url")
    var url : String = ""

    @ColumnInfo(name="title")
    var title : String = ""

}

@Dao
interface LinkDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLink(linkVO: LinkVO): Long

    @Query("SELECT * FROM Link WHERE url =:url")
    fun getLink(url: String) : LinkVO

    @Query("SELECT * FROM Link")
    fun getLinkList() : List<LinkVO>
}