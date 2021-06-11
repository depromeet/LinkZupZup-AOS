package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DonutHistoryEntity(

    @SerializedName("content")
    val content: ArrayList<MyPageBadgeEntity> = arrayListOf(),

    @SerializedName("pageable")
    val pageable: LinkAlarmPageableEntity,

    @SerializedName("totalPages")
    val totalPages: Int = 0,

    @SerializedName("totalElements")
    val totalElements: Int = 0,

    @SerializedName("last")
    val last: Boolean = false,

    @SerializedName("number")
    val number: Int = 0,

    @SerializedName("sort")
    val sort: LinkSortEntity,

    @SerializedName("size")
    val size: Int = 0,

    @SerializedName("numberOfElements")
    val numberOfElements: Int = 0,

    @SerializedName("first")
    val first: Boolean = false,

    @SerializedName("empty")
    val empty: Boolean = false):Parcelable
