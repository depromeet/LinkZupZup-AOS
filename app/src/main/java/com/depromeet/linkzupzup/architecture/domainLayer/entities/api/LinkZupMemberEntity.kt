package com.depromeet.linkzupzup.architecture.domainLayer.entities.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkZupMemberEntity(

    @SerializedName("createdAt")
    val createdAt: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("lastAccessedAt")
    val lastAccessedAt: String? = null,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("token")
    val token: String = "",

    @SerializedName("tokenExpiredTime")
    val tokenExpiredTime: String = "",

    @SerializedName("tokenStartTime")
    val tokenStartTime: String? = null,

    @SerializedName("totalPoint")
    val totalPoint: Int = 0,

    @SerializedName("totalReadCount")
    val totalReadCount: Int = 0): Parcelable