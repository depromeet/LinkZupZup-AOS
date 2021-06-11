package com.depromeet.linkzupzup.architecture.presenterLayer.model


data class MyPageData(
    var userName: String = "",
    var badgeUrl: String = "",
    var monthlyPoint: Int = 0,
    var totalReadCnt: Int = 0,
    var readCnt : Int = 0,
    var alarmEnabled : Boolean = false) {

    companion object{
        const val THIS_WEEK_POINT : Int = Int.MIN_VALUE
        const val TOTAL_READ_COUNT : Int = THIS_WEEK_POINT + 100
        const val THIS_WEEK_READ_COUNT : Int = TOTAL_READ_COUNT + 100

        val STR_THIS_WEEK_POINT : Pair<String,String> = ("\uD83C\uDF69 이번 달 내 포인트" to "p")
        val STR_TOTAL_READ_COUNT : Pair<String,String> = ("\uD83D\uDCDA 전체 읽은 수" to "개")
        val STR_THIS_WEEK_READ_COUNT : Pair<String,String> = ("\uD83D\uDCDA 이번 달 읽은 수" to "개")

        const val MENU_MOVE : Int = 0
        const val MENU_TOGGLE : Int = 1
        val MENU_DATA : List<Pair<String,Int>> = listOf(
            Pair("다 읽은 링크", MENU_MOVE),
            Pair("푸시 알림", MENU_TOGGLE),
            Pair("개인정보 처리방침", MENU_MOVE),
            Pair("칠성파가 누구? ⭐️",MENU_MOVE)
        )
    }
}