package com.depromeet.linkzupzup.architecture.presenterLayer.model

import androidx.compose.ui.graphics.Color
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.extensions.toDate
import com.depromeet.linkzupzup.ui.theme.TagBgColor01
import com.depromeet.linkzupzup.ui.theme.TagTextColor01
import java.util.*
import kotlin.collections.ArrayList

data class LinkData(
    var linkId: Int = 0,
    var userId: Int = 0,
    var linkURL: String = "",
    var linkTitle: String = "",
    var imgURL : String = "",
    var description : String = "",
    var hasReminder: Boolean = false,
    var hashtags: ArrayList<LinkHashData> = arrayListOf(),
    var createdAt: Date = Date(0),
    var completedAt: Date = Date(0),
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

    fun isMetaSet() : Boolean = linkTitle.isNotEmpty()


    fun setMetaInfo(metaEntity: LinkMetaInfoEntity) {
        this.linkTitle = metaEntity.title
        this.description = metaEntity.content
        this.imgURL = metaEntity.imgUrl
    }

    constructor(linkEntity: LinkAlarmEntity) : this() {
        this.linkURL = linkEntity.linkURL
        this.linkId = linkEntity.linkId
        this.userId = linkEntity.userId
        this.completed = linkEntity.completed
        // this.completedAt = linkEntity.completedAt.toDate("yyyy-MM-dd HH:mm:ss")
        // this.createdAt = linkEntity.completedAt.toDate("yyyy-MM-dd HH:mm:ss")

        this.hashtags.addAll(
            linkEntity.hashtags.map {
                LinkHashData(
                    it.hashtagId,
                    it.hashtagName,
                    it.createdAt,
                    TagColor(TagTextColor01,
                    TagBgColor01))
            }
        )

    }
}

data class LinkHashData(
    var hashtagId: Int = 0,
    var hashtagName: String = "",
    var createdAt: String = "",
    var tagColor: TagColor)