package com.depromeet.linkzupzup.extensions

import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.AlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkAlarmEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.architecture.presenterLayer.model.WeeklyAlarm

/** Presenter와 Domain 간의 의존성을 Domain으로만 향하도록 아래와 같이 mapper 메서드 생성 */
//fun UserEntity.mapToPresenter() = User(name, age)
//fun User.mapToDataLayer() = UserEntity(name= name, age= age)

fun LinkMetaInfoEntity.mapToPresenter() = LinkData(linkURL = url, linkTitle = title, imgURL = imgUrl, description = content, createdAt = createdDt)
fun LinkData.mapToDataLayer() = LinkMetaInfoEntity(url = linkURL, title = linkTitle, imgUrl = imgURL, content = description, createdDt = createdAt)
fun LinkAlarmEntity.mapToDataLayer() = LinkData(this)
fun ArrayList<AlarmEntity>.mapToDataLayer(): ArrayList<WeeklyAlarm> = ArrayList(map { WeeklyAlarm(it) })