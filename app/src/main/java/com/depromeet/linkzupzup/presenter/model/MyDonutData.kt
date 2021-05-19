package com.depromeet.linkzupzup.presenter.model

class MyDonutData<T : Any> {

    companion object{
        // LazyVerticalGrid 의 cells 속성 Fixed 값으로 활용
        const val NO_DONUT : Int = 1
        const val HAVE_DONUT : Int = 3

        /* 임시 */
        fun mockMyDonutContentList(cnt : Int): ArrayList<MyDonutData<DonutBadge>> {
            var cnt2 = cnt
            var idx = 0
            if(cnt2 > 12) cnt2 = 12

            return arrayListOf<MyDonutData<DonutBadge>>().apply {
                repeat(cnt2) {
                    add(MyDonutData(HAVE_DONUT, mockBadgeData[idx++]))
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