package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInUpEntity (

    @SerializedName("userId")
    var userId: Int = 0,

    @SerializedName("loginId")
    var loginId: Long = 0L,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("token")
    var token: String = "",

    @SerializedName("pushToken")
    var pushToken: String = ""): Parcelable