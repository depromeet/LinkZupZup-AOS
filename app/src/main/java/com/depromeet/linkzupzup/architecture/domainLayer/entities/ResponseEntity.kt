package com.depromeet.linkzupzup.architecture.domainLayer.entities

import com.google.gson.annotations.SerializedName

class ResponseEntity<T>(

    @SerializedName("status")
    var status: String = "",

    @SerializedName("comment")
    var comment: String = "",

    @SerializedName("data")
    var data: T? = null) {
    fun getStatus(): Int = status.toInt()
}