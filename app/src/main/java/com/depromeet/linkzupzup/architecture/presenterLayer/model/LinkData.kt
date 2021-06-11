package com.depromeet.linkzupzup.architecture.presenterLayer.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.ui.theme.*
import com.depromeet.linkzupzup.utils.CommonUtil
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class LinkData(
    var linkId: Int = -1,
    var userId: Int = -1,
    var linkURL: String = "",
    var linkTitle: String = "",
    var imgURL : String = "",
    var description : String = "",
    var hasReminder: Boolean = false,
    var hashtags: ArrayList<LinkHashData> = arrayListOf(),
    var createdAt: Date = Date(0),
    var completedAt: Date = Date(0),
    var completed: Boolean = false,
    var tagColor: TagColor = CommonUtil.getRandomeTagColor()): Parcelable {

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

        fun mockLinkList(): ArrayList<LinkData> = arrayListOf<LinkData>().apply {
            add(LinkData(linkURL = "https://brunch.co.kr/@dalgudot/94",
                linkTitle = "02화 UI 디자인을 위한 UX 원칙 5가지",
                imgURL = "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/6C10/image/tsRBVaNg7vWkcMZ4M_aMQSLpMr8.jpg",
                description = "디자인 독학하기 02 | UI/UX 디자인 경험을 공유합니다 :) [Contents] 01 실무 UX 디자인의 정의 02 모바일 UI 디자인을 위한 UX 원칙 1_ [대원칙] 쉽고, 쉽고, 쉽게 2_ 단순하게 3_ 자연스럽게 4_ 사용자 중심 글쓰기 5_ 버튼의 원칙 03 참고자료  최근 한 IT 스타트업에서 UI/UX 디자이너로 일하기 시작했다. 스타트업인 만큼 입사 첫날부터 바"))
            add(LinkData(linkURL = "https://brunch.co.kr/@delight412/351",
                linkTitle = "스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까",
                imgURL = "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/ZVA/image/Z99yCKXyWxClKX5FhzSF0IZlVEg.jpg",
                description = "이직을 하면 새로운 회사에 적응하는 것이 만만한 일은 아니다. 문화적인 변화가 주는 부담감이 적지 않다. 개인적으로도 그랬던 것 같다.  잉글랜드 프리미어리그에선 잘 나가던 축구 선수가 스페인 프리메라리가로 이적하면 기대 이하의 성적을 보이는 경우가 종종 있는 것처럼 기업도 마찬가지지 싶다. 특히 회사 규모가 다를 경우 변화의 충격은 더욱 클 수 있다."))
        }

        fun ArrayList<LinkAlarmEntity>.converter(): ArrayList<LinkData> = ArrayList(map { LinkData(it) })
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
                val randomTag = CommonUtil.getRandomeTagColor()
                LinkHashData(
                    it.hashtagId,
                    it.hashtagName,
                    it.createdAt,
                    TagColor(randomTag.bgColor,
                    randomTag.textColor))
            }
        )

        this.linkTitle = linkEntity.metaTitle
        this.description = linkEntity.metaDescription
        this.imgURL = linkEntity.metaImageUrl

    }
}

@Parcelize
data class LinkHashData(
    var hashtagId: Int = 0,
    var hashtagName: String = "",
    var createdAt: String = "",
    var tagColor: TagColor = CommonUtil.getRandomeTagColor()): Parcelable {

    companion object {
        val tc1: List<LinkHashData> = listOf(
            LinkHashData(0, "디자인", "", TagColor(TagBgColor01, TagTextColor01)),
            LinkHashData(1, "포트폴리오", "", TagColor(TagBgColor02, TagTextColor02)),
            LinkHashData(2, "UX", "", TagColor(TagBgColor03, TagTextColor03)),
            LinkHashData(3, "UI", "", TagColor(TagBgColor04, TagTextColor04)),
            LinkHashData(4, "마케팅", "", TagColor(TagBgColor05, TagTextColor05)),
            LinkHashData(5, "인공지능", "", TagColor(TagBgColor06, TagTextColor06))
        )

        val tc2: List<LinkHashData> = listOf(
            LinkHashData(6, "프론트 개발", "", TagColor(TagBgColor07, TagTextColor07)),
            LinkHashData(7, "그로스 해킹", "", TagColor(TagBgColor03, TagTextColor03)),
            LinkHashData(8, "Android", "", TagColor(TagBgColor01, TagTextColor01)),
            LinkHashData(9, "스타트업", "", TagColor(TagBgColor02, TagTextColor02)),
            LinkHashData(10, "ios", "", TagColor(TagBgColor04, TagTextColor04))
        )
    }

}