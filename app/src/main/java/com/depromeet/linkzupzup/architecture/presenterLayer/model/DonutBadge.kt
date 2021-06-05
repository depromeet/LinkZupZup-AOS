package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.extensions.digitFormat1000
import java.util.*

data class DonutBadge (
    var badgeResource: Int = R.drawable.ic_donut01,
    var point: Int = 5235,
    var date: String = "2021년 4월",
    var startPoint : Int = 0,
    var endPoint : Int = 0){

    fun getInfoStr() : String{
        if(startPoint==0)
            return "\uD83D\uDC49 $startPoint point (기본)"
        if(endPoint == -1)
            return "\uD83D\uDC49 ${startPoint.digitFormat1000()} point 이상 적립 달성 시"
        return "\uD83D\uDC49 ${startPoint.digitFormat1000()} ~ ${endPoint.digitFormat1000()} point 적립 달성 시"
    }
}

/**
 * 임시
 */
val mockDonutTypes = arrayListOf(
    R.drawable.ic_donut05,
    R.drawable.ic_donut01,
    R.drawable.ic_donut02,
    R.drawable.ic_donut06,
    R.drawable.ic_donut03,
    R.drawable.ic_donut05,
    R.drawable.ic_donut02,
    R.drawable.ic_donut05,
    R.drawable.ic_donut04,
    R.drawable.ic_donut04,
    R.drawable.ic_donut05,
    R.drawable.ic_donut05)

fun getRandomMockDonutImgRes(): Int = mockDonutTypes[Random().nextInt(mockDonutTypes.size)]
fun getMockBadgeData(imgRes: Int): DonutBadge = DonutBadge(imgRes)