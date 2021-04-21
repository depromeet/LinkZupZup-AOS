package com.depromeet.linkzupzup.dataSources.dao

import com.depromeet.linkzupzup.presenter.model.VersionResponse
import com.depromeet.linkzupzup.ApiUrl
import io.reactivex.Observable
import retrofit2.http.POST

interface TestUserDao {

    @POST(ApiUrl.SEARCH_REPOSITORIES)
    fun getVersionInfo(): Observable<VersionResponse>

}