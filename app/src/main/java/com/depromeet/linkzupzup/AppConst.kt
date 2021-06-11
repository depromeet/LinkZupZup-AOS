package com.depromeet.linkzupzup

object AppConst {

    const val AUTHORIZATION_KEY: String = "Authorization"
    const val USER_ID_KEY: String = "userId"
    const val LOGIN_ID: String = "loginId"
    const val USER_NAME_KEY: String = "userName"
    const val USER_FCM_TOKEN: String = "fcmToken"
    const val DEVICE_TYPE_KEY: String = "device_type"
    const val DEVICE_TYPE: String = "AOS"
    const val KAKAO_TOKEN_KEY: String = "kakao_token"
    const val TODAY_READ_KEY: String = "todayReadCount"
    const val PUSH_AGREE_KEY: String = "pushAgree"

    const val OAUTH_KAKAO_TYPE: String = "oauth_kakao_type"
    const val OAUTH_GOOGLE_TYPE: String = "oauth_google_type"

    const val LINK_ID = "link_id"
    const val LINK_URL = "link_url"

    const val LINK_ITEM = "link_item"
    const val WEB_LINK_URL = "web_link_url"
    const val WEB_LINK_ID = "web_link_id"

    // TCP Handshake가 완료되기까지 지속되는 시간
    const val CONNECTION_TIMEOUT: Long = 15
    // 서버로부터 응답까지의 시간이 READ_TIMEOUT을 초과하면 실패로 간주
    const val READ_TIMEOUT: Long = 15
    // 클라이언트로 부터 서버로 응답을 보내는 시간이 WRITE_TIMEOUT을 초과하면 실패로 간주
    const val WRITE_TIMEOUT: Long = 15

    const val HASH_TAG_MAX_LIMIT = 3


}