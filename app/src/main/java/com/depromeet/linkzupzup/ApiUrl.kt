package com.depromeet.linkzupzup

object ApiUrl {

    const val BASE_DOMAIN: String = "http://ec2-54-180-30-9.ap-northeast-2.compute.amazonaws.com:8080"

    const val VERSION = "v1"

    const val BASE_URL = "$BASE_DOMAIN/$VERSION"

    const val LINK_LIST = "$BASE_URL/links"

}