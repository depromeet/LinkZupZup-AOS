package com.depromeet.linkzupzup

object AppConst {

    const val AUTHHORIZATION_KEY: String = "Authorization"
    const val USER_ID_KEY: String = "userId"
    const val DEVICE_TYPE_KEY: String = "device_type"
    const val DEVICE_TYPE: String = "AOS"


    // TCP Handshake가 완료되기까지 지속되는 시간
    const val CONNECTION_TIMEOUT: Long = 15
    // 서버로부터 응답까지의 시간이 READ_TIMEOUT을 초과하면 실패로 간주
    const val READ_TIMEOUT: Long = 15
    // 클라이언트로 부터 서버로 응답을 보내는 시간이 WRITE_TIMEOUT을 초과하면 실패로 간주
    const val WRITE_TIMEOUT: Long = 15

}