package com.depromeet.linkzupzup.architecture.domainLayer.entities

import com.google.gson.annotations.SerializedName

class ResponseEntity<T>(

    @SerializedName("status")
    override var status: String = "",

    @SerializedName("comment")
    override var comment: String = "",

    @SerializedName("data")
    var data: T? = null): DefaultResponseEntity