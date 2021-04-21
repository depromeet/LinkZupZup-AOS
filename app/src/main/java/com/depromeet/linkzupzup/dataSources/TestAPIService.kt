package com.depromeet.linkzupzup.dataSources

import com.depromeet.linkzupzup.presenter.model.VersionResponse
import com.depromeet.linkzupzup.ApiUrl
import io.reactivex.Observable
import retrofit2.http.POST

interface TestAPIService {

    @POST(ApiUrl.SEARCH_REPOSITORIES)
    fun getVersionInfo(): Observable<VersionResponse>

}