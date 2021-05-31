package com.depromeet.linkzupzup.architecture.domainLayer.entities.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmRegistEntity(

    @SerializedName("alarmId")
    val alarmStatus: String? = null,

    @SerializedName("alarmId")
    val createdAt: String = "",

    @SerializedName("alarmId")
    val id: Int = 0,

    @SerializedName("alarmId")
    val member: LinkZupMemberEntity = LinkZupMemberEntity(),

    @SerializedName("alarmId")
    val notifyTime: String = "",

    @SerializedName("alarmId")
    val repeatedDate: String? = null): Parcelable