package com.depromeet.linkzupzup.presenter.model

import com.google.gson.annotations.SerializedName

data class VersionData (

    @SerializedName("_idx")
    var idx: String = "",

    @SerializedName("message")
    var message: String = "",

    @SerializedName("verMessage")
    var verMessage: String = "",

    @SerializedName("version")
    var version: String = "",

    @SerializedName("isForceUpdate")
    var isForceUpdate: String = "",

    @SerializedName("code")
    var code: String = "",

    @SerializedName("redirect")
    var redirect: String = "")
