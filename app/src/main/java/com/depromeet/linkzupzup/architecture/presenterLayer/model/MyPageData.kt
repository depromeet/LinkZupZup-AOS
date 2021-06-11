package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.MyPageInfoResponseEntity
import com.depromeet.linkzupzup.view.linkHistory.LinkHistoryDetailActivity
import com.depromeet.linkzupzup.view.termsAndInfo.TermsAndInfoActivity


data class MyPageData(
    var userName: String = "",
    var badgeUrl: String = "",
    var monthlyPoint: Int = 0,
    var totalReadCnt: Int = 0,
    var readCnt : Int = 0,
    var alarmEnabled : Boolean = false) {

    companion object {
        const val THIS_WEEK_POINT : Int = Int.MIN_VALUE
        const val TOTAL_READ_COUNT : Int = THIS_WEEK_POINT + 100
        const val THIS_WEEK_READ_COUNT : Int = TOTAL_READ_COUNT + 100

        val STR_THIS_WEEK_POINT : Pair<String,String> = ("\uD83C\uDF69 이번 달 내 포인트" to "p")
        val STR_TOTAL_READ_COUNT : Pair<String,String> = ("\uD83D\uDCDA 전체 읽은 수" to "개")
        val STR_THIS_WEEK_READ_COUNT : Pair<String,String> = ("\uD83D\uDCDA 이번 달 읽은 수" to "개")

        const val MENU_MOVE : Int = 0
        const val MENU_TOGGLE : Int = 1

        const val PRIVATE_POLICY_LINK = "https://www.notion.so/b099119/29e8ed9564ec4fb0aafb0bca48c5553d"
        const val SEVENSTAR_INTRODUCE_LINK = "https://www.notion.so/d07ca6a626a74ac3ac7a2ce2e83f9e04"

        val MENU_DATA : List<MyPageMenuData> = listOf(
            MyPageMenuData("다 읽은 링크", MENU_MOVE, LinkHistoryDetailActivity::class.java),
            MyPageMenuData("푸시 알림", MENU_TOGGLE),
            MyPageMenuData("개인정보 처리방침", MENU_MOVE, TermsAndInfoActivity::class.java, PRIVATE_POLICY_LINK),
            MyPageMenuData("칠성파가 누구? ⭐️",MENU_MOVE, TermsAndInfoActivity::class.java, SEVENSTAR_INTRODUCE_LINK)
        )
    }


    constructor(myPageInfoResponseEntity: MyPageInfoResponseEntity): this() {
        this.userName = myPageInfoResponseEntity.nickName
        this.badgeUrl = myPageInfoResponseEntity.badge.badgeURL
        this.monthlyPoint = myPageInfoResponseEntity.totalPoint
        this.readCnt = myPageInfoResponseEntity.totalReadCount
        this.alarmEnabled = myPageInfoResponseEntity.alarmEnabled
    }
}

data class MyPageMenuData(
    var menuTitle: String = "",
    var menuType: Int = 0,
    var moveClass: Class<*>? = null,
    var url: String? = "")