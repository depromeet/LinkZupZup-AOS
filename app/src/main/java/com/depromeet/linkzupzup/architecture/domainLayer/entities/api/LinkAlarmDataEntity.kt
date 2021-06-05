package com.depromeet.linkzupzup.architecture.domainLayer.entities.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkAlarmDataEntity(

    @SerializedName("content")
    var content: ArrayList<LinkAlarmEntity> = arrayListOf(),

    @SerializedName("empty")
    val empty: Boolean = false,

    @SerializedName("first")
    val first: Boolean = false,

    @SerializedName("last")
    val last: Boolean = false,

    @SerializedName("number")
    val number: Int = 0,

    @SerializedName("numberOfElements")
    val numberOfElements: Int = 0,

    @SerializedName("pageable")
    val pageable: LinkAlarmPageableEntity = LinkAlarmPageableEntity(),

    @SerializedName("size")
    val size: Int = 0,

    @SerializedName("sort")
    val sort: LinkSortEntity = LinkSortEntity(),

    @SerializedName("totalElements")
    val totalElements: Int = 0,

    @SerializedName("totalPages")
    val totalPages: Int = 0): Parcelable