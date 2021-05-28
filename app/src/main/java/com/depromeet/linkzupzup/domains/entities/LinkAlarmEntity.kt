package com.depromeet.linkzupzup.domains.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkAlarmEntity (

    @SerializedName("linkId")
    var linkId: Int = -1,

    @SerializedName("userId")
    var userId: Int = -1,

    @SerializedName("linkURL")
    var linkURL: String = "",

    @SerializedName("hashtags")
    var hashtags: ArrayList<HashTagEntity> = arrayListOf(),

    @SerializedName("createdAt")
    var createdAt: String = "",

    @SerializedName("completedAt")
    var completedAt: String = "",

    @SerializedName("completed")
    var completed: Boolean = false): Parcelable