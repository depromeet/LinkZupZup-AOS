package com.depromeet.linkzupzup.architecture.presenterLayer.model

class MyDonutData<T : Any> {

    companion object{
        // LazyVerticalGrid 의 cells 속성 Fixed 값으로 활용
        const val NO_DONUT : Int = 1
        const val HAVE_DONUT : Int = 3

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