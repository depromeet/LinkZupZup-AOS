package com.depromeet.linkzupzup.domains.entities


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkSortEntity(

    @SerializedName("empty")
    val empty: Boolean = false,

    @SerializedName("sorted")
    val sorted: Boolean = false,

    @SerializedName("unsorted")
    val unsorted: Boolean = false): Parcelable