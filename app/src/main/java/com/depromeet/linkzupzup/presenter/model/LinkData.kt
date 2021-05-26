package com.depromeet.linkzupzup.presenter.model

import androidx.compose.ui.graphics.Color

data class LinkData(
    var linkId: Int = 0,
    var userId: Int = 0,
    var linkURL: String = "",
    var linkTitle: String = "",
    var imgURL : String = "",
    var description : String = "",
    var hasReminder: Boolean = false,
    var hashtags: ArrayList<LinkHashData> = arrayListOf(),
    var createdAt: String = "",
    var completedAt: String = "",
    var completed: Boolean = false) {

    /* 향후 삭제 예정 */
    companion object {
        fun mockData(): LinkData {
            arrayListOf<LinkHashData>().apply {
                add(LinkHashData(hashtagName = "디자인", tagColor = TagColor(Color(0xFFFFECEC), Color(0xFFE88484))))
                add(LinkHashData(hashtagName = "포토폴리오", tagColor = TagColor(Color(0xFFD9F8F4), Color(0xFF57B9AF))))
            }.let { hashDataList ->
                return LinkData(hashtags = hashDataList)
            }
        }
    }
}

data class LinkHashData(
    var hashtagId: Int = 0,
    var hashtagName: String = "",
    var createdAt: String = "",
    var tagColor: TagColor)