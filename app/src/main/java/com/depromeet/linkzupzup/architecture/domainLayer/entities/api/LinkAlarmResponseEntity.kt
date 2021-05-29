package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkAlarmResponseEntity (

    @SerializedName("status")
    var status: String = "",

    @SerializedName("comment")
    var comment: String = "",

    @SerializedName("data")
    var data: LinkAlarmDataEntity? = null): Parcelable