package com.depromeet.linkzupzup.extensions

import com.depromeet.linkzupzup.domains.entities.LinkEntity
import com.depromeet.linkzupzup.domains.entities.UserEntity
import com.depromeet.linkzupzup.presenter.model.LinkData
import com.depromeet.linkzupzup.presenter.model.User

/** Presenter와 Domain 간의 의존성을 Domain으로만 향하도록 아래와 같이 mapper 메서드 생성 */
fun UserEntity.mapToPresenter() = User(name, age)
fun User.mapToDataLayer() = UserEntity(name= name, age= age)

fun LinkEntity.mapToPresenter() = LinkData(linkURL = url)
fun LinkData.mapToDataLayer() = LinkEntity(url = linkURL)
