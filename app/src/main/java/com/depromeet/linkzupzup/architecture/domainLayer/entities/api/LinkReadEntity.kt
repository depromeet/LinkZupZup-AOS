package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkReadEntity(

    @SerializedName("completed")
    val completed: Boolean = false,

    @SerializedName("seasonCount")
    val seasonCount: Int = 1): Parcelable