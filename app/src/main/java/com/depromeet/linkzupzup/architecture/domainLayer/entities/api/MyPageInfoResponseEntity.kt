package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPageInfoResponseEntity (

    @SerializedName("badge")
    val badge: MyPageBadgeEntity,

    @SerializedName("userId")
    val userId: Int = 0,

    @SerializedName("createdAt")
    val createdAt: String = "",

    @SerializedName("nickName")
    val nickName: String = "",

    @SerializedName("totalPoint")
    val totalPoint: Int = 0,

    @SerializedName("totalReadCount")
    val totalReadCount: Int = 0,

    @SerializedName("seasonCount")
    val seasonCount: Int = 0,

    @SerializedName("alarmEnabled")
    val alarmEnabled: Boolean = false): Parcelable