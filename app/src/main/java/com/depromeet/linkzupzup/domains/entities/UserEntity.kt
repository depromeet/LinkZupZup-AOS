package com.depromeet.linkzupzup.domains.entities

import com.google.gson.annotations.SerializedName

data class UserEntity (
    @SerializedName("idx")
    var idx: Int = -1,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("age")
    var age: Int = 0)