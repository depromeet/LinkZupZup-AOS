package com.depromeet.linkzupzup.domains.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HashTagEntity (

    @SerializedName("hashtagId")
    var hashtagId: Int = -1,

    @SerializedName("hashtagName")
    var hashtagName: String = "",

    @SerializedName("createdAt")
    var createdAt: String = ""): Parcelable