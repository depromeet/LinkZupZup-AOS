package com.depromeet.linkzupzup.architecture.domainLayer.entities.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmEntity (

    @SerializedName("alarmId")
    val alarmId: Int = 0,

    @SerializedName("createdAt")
    val createdAt: String = "",

    @SerializedName("enabled")
    val enabled: Boolean = false,

    @SerializedName("notifyTime")
    val notifyTime: String = "",

    @SerializedName("repeatedDate")
    val repeatedDate: String = "",

    @SerializedName("userId")
    val userId: Int = 0): Parcelable