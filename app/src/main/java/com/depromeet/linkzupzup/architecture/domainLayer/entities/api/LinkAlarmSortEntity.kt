package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkAlarmSortEntity(

    @SerializedName("empty")
    val empty: Boolean = false,

    @SerializedName("sorted")
    val sorted: Boolean = false,

    @SerializedName("unsorted")
    val unsorted: Boolean = false): Parcelable