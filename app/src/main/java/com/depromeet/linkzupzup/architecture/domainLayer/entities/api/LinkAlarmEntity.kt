package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

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
    var completed: Boolean = false,

    /**
     * UI용 MetaData
     */
    var metaTitle: String = "",
    var metaImageUrl: String = "",
    var metaDescription: String = ""): Parcelable