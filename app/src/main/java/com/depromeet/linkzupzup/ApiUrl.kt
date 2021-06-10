package com.depromeet.linkzupzup

object ApiUrl {

    private const val DEV_PORT = "9090"
    private const val LIVE_PORT = "8080"

    @JvmStatic
    val IS_DEV = !BuildConfig.DEBUG
    private const val IS_SSL = false
    private const val DOMAIN = "54.180.27.142"

    // const val BASE_DOMAIN: String = "http://192.168.2.58:8080"
    const val VERSION = "v1"

    @JvmStatic
    val BASE_DOMAIN: String = getBaseDomain()

    @JvmStatic
    fun getBaseDomain(): String = "${if(IS_SSL) "https" else "http"}://$DOMAIN:${if(!IS_DEV) LIVE_PORT else DEV_PORT}"

    const val LINKS = "links"
    const val ALARM = "alarm"
    const val ALARMS = "alarms"
    const val MEMBERS = "members"
    const val RANKING = "ranking"
    const val LOGIN = "login"
    const val COUNT = "count"
    const val MYPAGE = "mypage"
    const val INFO = "info"

    /**
     * Link
     */
    const val LINK_LIST = "/$VERSION/$LINKS"
    const val LINK_REGISTER = "/$VERSION/$LINKS"
    const val LINK_READ = "/$VERSION/$LINKS/"
    const val LINK_COUNT = "/$VERSION/$LINKS/$COUNT"

    /**
     * Alarm
     */
    const val ALARM_LIST = "/$VERSION/$ALARMS"
    const val ALARM_REGIST = "/$VERSION/$ALARMS"
    const val ALARM_DETAIL = "/$VERSION/$ALARMS"
    const val ALARM_UPDATE = "/$VERSION/$ALARMS"
    const val ALARM_DELETE = "/$VERSION/$ALARMS"

    /**
     * Members
     * 첫 호출은 회원가입되고 (token은 공백, userId는 상수 ( 0) ),
     * 응답으로 내려오는 token과 userId를 body에 포함하여 재호출시 로그인
     */
    const val MEMBERS_SIGN_IN = "/$VERSION/$MEMBERS/$LOGIN"
    const val MEMBERS_MYPAGE_INFO = "/$VERSION/$MEMBERS/$MYPAGE/$INFO"

    /**
     * Ranking
     */


}