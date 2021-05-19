package com.depromeet.linkzupzup.presenter.model

import com.depromeet.linkzupzup.R
import java.util.*

data class DonutBadge (
    var badgeResource: Int = R.drawable.ic_donut01,
    var point: Int = 5235,
    var date: String = "2021년 4월")

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