package com.depromeet.linkzupzup.architecture.domainLayer.entities.api


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignResponseEntity(

    @SerializedName("token")
    val token: String = "",

    @SerializedName("userId")
    val userId: Int = 0): Parcelable