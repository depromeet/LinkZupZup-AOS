package com.depromeet.linkzupzup.architecture.dataLayer.dao

import com.depromeet.linkzupzup.architecture.presenterLayer.model.VersionResponse
import io.reactivex.Observable

interface TestUserDao {

    fun getVersionInfo(): Observable<VersionResponse>

}