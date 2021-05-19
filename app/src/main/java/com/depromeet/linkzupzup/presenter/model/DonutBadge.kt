package com.depromeet.linkzupzup.presenter.model

import com.depromeet.linkzupzup.R

data class DonutBadge (
    var badgeResource: Int = R.drawable.ic_donut01,
    var point: Int = 5235,
    var date: String = "2021년 4월")

/* 임시 */
val mockBadgeData = arrayListOf(
    DonutBadge(R.drawable.ic_donut05),
    DonutBadge(R.drawable.ic_donut01),
    DonutBadge(R.drawable.ic_donut02),
    DonutBadge(R.drawable.ic_donut06),
    DonutBadge(R.drawable.ic_donut03),
    DonutBadge(R.drawable.ic_donut05),
    DonutBadge(R.drawable.ic_donut02),
    DonutBadge(R.drawable.ic_donut05),
    DonutBadge(R.drawable.ic_donut04),
    DonutBadge(R.drawable.ic_donut04),
    DonutBadge(R.drawable.ic_donut05),
    DonutBadge(R.drawable.ic_donut05)
)