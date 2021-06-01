package com.depromeet.linkzupzup.architecture.domainLayer.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import java.util.Date

@Entity(tableName = "LinkMetaInfo",
    primaryKeys = ["meta_url"],
    indices = [Index(value = ["meta_url"],unique = true)])
class LinkMetaInfoEntity (

    @ColumnInfo(name="meta_url")
    var url : String = "",

    @ColumnInfo(name="meta_title")
    var title : String = "",

    @ColumnInfo(name="meta_content")
    var content : String = "",

    @ColumnInfo(name="meta_img_url")
    var imgUrl : String = "",

    @ColumnInfo(name="meta_created_dt")
    var createdDt : Date

    )