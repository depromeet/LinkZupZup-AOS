package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPageBadgeEntity(

    @SerializedName("badgeId")
    val badgeId: Int = 0,

    @SerializedName("badgeURL")
    val badgeURL: String = "",

    @SerializedName("badgeName")
    val badgeName: String = "",

    @SerializedName("category")
    val category: String = "",

    @SerializedName("conditions")
    val conditions: String = "",

    @SerializedName("createdAt")
    val createdAt: String = ""): Parcelable
