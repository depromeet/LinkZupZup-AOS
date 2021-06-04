package com.depromeet.linkzupzup.architecture.presenterLayer.model

class MainContentData <T: Any> {

    companion object {
        const val EMPTY_TYPE = Int.MIN_VALUE
        const val MAIN_TOP_HEADER = EMPTY_TYPE + 100
        const val MAIN_LINK_ITEM = MAIN_TOP_HEADER + 100

        fun mockMainContentList(cnt: Int): ArrayList<MainContentData<LinkData>> {
            return arrayListOf<MainContentData<LinkData>>().apply {
                repeat(cnt) {
                    add(MainContentData(MAIN_LINK_ITEM, LinkData.mockData()))
                }
            }
        }

        fun mockLinkDataList(cnt: Int): ArrayList<LinkData> = arrayListOf<LinkData>().apply {
            repeat(cnt) {
                add(LinkData.mockData())
            }
        }
    }

    var type: Int = EMPTY_TYPE
    var data: T? = null

    constructor()
    constructor(type: Int, data: T? = null) {
        this.type = type
        this.data = data
    }
}