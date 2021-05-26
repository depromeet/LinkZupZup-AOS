package com.depromeet.linkzupzup.domains.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Link")
class LinkEntity (

    @SerializedName("url")
    @PrimaryKey
    @ColumnInfo(name="link_url")
    var url : String = "",

    @SerializedName("title")
    @ColumnInfo(name="link_title")
    var title : String = "",


    @SerializedName("description")
    @ColumnInfo(name="link_description")
    var description : String = "",

    @SerializedName("imgUrl")
    @ColumnInfo(name="link_imgurl")
    var imgUrl : String = ""


)

