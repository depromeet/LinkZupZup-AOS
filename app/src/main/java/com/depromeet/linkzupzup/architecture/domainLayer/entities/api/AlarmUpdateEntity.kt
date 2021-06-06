package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmUpdateEntity (

    @SerializedName("enabled")
    val enabled: Boolean = false,

    @SerializedName("notifyTime")
    val notifyTime: String = "",

    @SerializedName("repeatedDate")
    val repeatedDate: String = ""): Parcelable