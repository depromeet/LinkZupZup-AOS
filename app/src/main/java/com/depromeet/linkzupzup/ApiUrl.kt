package com.depromeet.linkzupzup

object ApiUrl {

    const val BASE_DOMAIN: String = "http://54.180.27.142:8080"
    // const val BASE_DOMAIN: String = "http://192.168.2.58:8080"
    const val VERSION = "v1"
    const val BASE_URL = "$BASE_DOMAIN/$VERSION"

    const val LINKS = "links"
    const val ALARM = "alarm"
    const val ALARMS = "alarms"
    const val MEMBERS = "members"
    const val RANKING = "ranking"
    const val COUNT = "count"

    /**
     * Link
     */
    const val LINK_LIST = "$BASE_URL/$LINKS"
    const val LINK_COUNT = "$BASE_URL/$LINKS/$COUNT"

    /**
     * Alarm
     */
    const val ALARM_LIST = "$BASE_URL/$ALARMS"
    const val ALARM_REGIST = "$BASE_URL/$ALARMS"
    const val ALARM_DETAIL = "$BASE_URL/$ALARMS"
    const val ALARM_UPDATE = "$BASE_URL/$ALARMS"
    const val ALARM_DELETE = "$BASE_URL/$ALARMS"

    /**
     * Members
     * 첫 호출은 회원가입되고 (token은 공백, userId는 상수 ( 0) ),
     * 응답으로 내려오는 token과 userId를 body에 포함하여 재호출시 로그인
     */
    const val MEMBERS_SIGN_IN = "$BASE_URL/$MEMBERS/login"

    /**
     * Ranking
     */


}