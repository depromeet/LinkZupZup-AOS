package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkAlarmPageableEntity(

    @SerializedName("offset")
    val offset: Int = -1,

    @SerializedName("pageNumber")
    val pageNumber: Int = -1,

    @SerializedName("pageSize")
    val pageSize: Int = -1,

    @SerializedName("paged")
    val paged: Boolean = false,

    @SerializedName("sort")
    val sort: LinkAlarmSortEntity? = null,

    @SerializedName("unpaged")
    val unpaged: Boolean = false): Parcelable