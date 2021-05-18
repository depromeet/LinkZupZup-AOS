package com.depromeet.linkzupzup.presenter.model

class MypageData {

    companion object{
        const val THIS_WEEK_POINT : Int = Int.MIN_VALUE
        const val TOTAL_READ_COUNT : Int = THIS_WEEK_POINT + 100
        const val THIS_WEEK_READ_COUNT : Int = TOTAL_READ_COUNT + 100

        val STR_THIS_WEEK_POINT : Map<String,String> = mapOf("\uD83C\uDF69 이번 달 내 포인트" to "p")
        val STR_TOTAL_READ_COUNT : Map<String,String> = mapOf("\uD83D\uDCDA 전체 읽은 수" to "개")
        val STR_THIS_WEEK_READ_COUNT : Map<String,String> = mapOf("\uD83D\uDCDA 이번 달 읽은 수" to "개")
    }
}