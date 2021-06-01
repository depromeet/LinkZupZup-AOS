package com.depromeet.linkzupzup.architecture.domainLayer.entities

import com.google.gson.annotations.SerializedName

class ResponseArrayEntity<T> (

    @SerializedName("status")
    override var status: String = "",

    @SerializedName("comment")
    override var comment: String = "",

    @SerializedName("data")
    var data: ArrayList<T> = arrayListOf()): DefaultResponseEntity