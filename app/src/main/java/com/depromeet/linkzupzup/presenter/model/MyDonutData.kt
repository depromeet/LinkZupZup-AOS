package com.depromeet.linkzupzup.presenter.model

import com.depromeet.linkzupzup.R

class MyDonutData<T : Any> {

    companion object{
        // LazyVerticalGrid 의 cells 속성 Fixed 값으로 활용
        const val NO_DONUT : Int = 1
        const val HAVE_DONUT : Int = 3

        val donutInfo : List<DonutBadge?> = listOf(
            DonutBadge(),
            DonutBadge(badgeResource = R.drawable.ic_donut02, startPoint = 100, endPoint = 1999),
            DonutBadge(badgeResource = R.drawable.ic_donut03, startPoint = 2000, endPoint = 2999),
            DonutBadge(badgeResource = R.drawable.ic_donut04, startPoint = 3000, endPoint = 4999),
            DonutBadge(badgeResource = R.drawable.ic_donut05, startPoint = 5000, endPoint = 9999),
            DonutBadge(badgeResource = R.drawable.ic_donut06, startPoint = 100000, endPoint = -1),
            null
        )

        /* 임시 */
        fun mockMyDonutContentList(cnt : Int): ArrayList<MyDonutData<DonutBadge>> {
            return arrayListOf<MyDonutData<DonutBadge>>().apply {
                repeat(cnt) {
                    val badgeImgRes: Int = getRandomMockDonutImgRes()
                    val donutBadge: DonutBadge = getMockBadgeData(badgeImgRes)
                    add(MyDonutData(HAVE_DONUT, donutBadge))
                }
            }
        }
    }

    var type : Int = NO_DONUT
    var data : Any? = null
    constructor()
    constructor(type : Int, data : Any? = null){
        this.type = type
        this.data = data
    }

}