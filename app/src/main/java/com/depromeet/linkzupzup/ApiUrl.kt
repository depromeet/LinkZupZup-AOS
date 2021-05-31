package com.depromeet.linkzupzup

object ApiUrl {

    const val BASE_DOMAIN: String = "http://ec2-54-180-30-9.ap-northeast-2.compute.amazonaws.com:8080"
    const val VERSION = "v1"
    const val BASE_URL = "$BASE_DOMAIN/$VERSION"

    const val LINKS = "links"
    const val ALARM = "alarm"
    const val ALARMS = "alarms"
    const val MEMBERS = "members"
    const val RANKING = "ranking"

    /**
     * Link
     */
    const val LINK_LIST = "$BASE_URL/$LINKS"

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
     */
    const val MEMBERS_SIGN_IN = "$BASE_URL/$MEMBERS/login"

    const val MEMBERS_SIGN_UP = "$BASE_URL/$MEMBERS/register"

    /**
     * Ranking
     */


}