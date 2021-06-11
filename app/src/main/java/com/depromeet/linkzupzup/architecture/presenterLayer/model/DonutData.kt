package com.depromeet.linkzupzup.architecture.presenterLayer.model

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.DonutBadgeEntity
import com.depromeet.linkzupzup.extensions.dateStrToFormatStr
import com.depromeet.linkzupzup.utils.DLog


data class DonutData(
    var badgeUrl: String = "",
    var seasonPoint: Int = 0,
    var createdAt: String = ""
){

    constructor(donutBadgeEntity: DonutBadgeEntity) : this() {
        this.badgeUrl = donutBadgeEntity.badgeURL
        this.seasonPoint = donutBadgeEntity.seasonalPoint
        this.createdAt = donutBadgeEntity.createdAt.dateStrToFormatStr() /// YYYY-mm-dd HH:ii:ss

        DLog.e("DATE",donutBadgeEntity.createdAt.dateStrToFormatStr())

    }


}