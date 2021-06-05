package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkRegisterEntity(

    @SerializedName("linkURL")
    var linkURL: String = "",

    @SerializedName("hashtags")
    var hashtags: ArrayList<String> = arrayListOf()): Parcelable
