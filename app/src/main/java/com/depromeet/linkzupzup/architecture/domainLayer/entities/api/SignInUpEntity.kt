package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInUpEntity (

    @SerializedName("email")
    var email: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("token")
    var token: String = "",

    @SerializedName("userId")
    var userId: Int = 0): Parcelable