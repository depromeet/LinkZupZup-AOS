package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.depromeet.linkzupzup.AppConst
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInUpEntity (

    @SerializedName("loginId")
    var loginId: String = "",

    @SerializedName("socialType")
    var socialType: String = AppConst.SOCIAL_KAKAO_TYPE,

    @SerializedName("deviceType")
    var deviceType: String = AppConst.DEVICE_TYPE,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("pushToken")
    var pushToken: String = "",

    @SerializedName("token")
    var token: String? = null,

    @SerializedName("userId")
    var userId: Int = 0): Parcelable