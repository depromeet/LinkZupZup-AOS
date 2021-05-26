package com.depromeet.linkzupzup.domains.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Link")
class LinkEntity (

    @SerializedName("url")
    @PrimaryKey
    @ColumnInfo(name="url")
    var url : String = "",

    @SerializedName("title")
    @ColumnInfo(name="title")
    var title : String = ""
)

