package com.depromeet.linkzupzup.domains.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "Link",
    indices = [Index(value = ["link_url"])])
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

    @SerializedName("img")
    @ColumnInfo(name="link_img")
    var imgUrl : String = "",

    @SerializedName("completed")
    @ColumnInfo(name="link_completed")
    var completed : Boolean = false,

    @SerializedName("created_at")
    @ColumnInfo(name="link_created_at")
    var createdAt : Long = 0,

    @SerializedName("completed_at")
    @ColumnInfo(name="link_completed_at")
    var completedAt : Long = 0
)

