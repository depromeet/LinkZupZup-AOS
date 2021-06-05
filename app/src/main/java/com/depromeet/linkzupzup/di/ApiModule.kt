package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.api.AlarmAPIService
import com.depromeet.linkzupzup.architecture.dataLayer.api.LinkAPIService
import com.depromeet.linkzupzup.architecture.dataLayer.api.MemberAPIService
import com.depromeet.linkzupzup.architecture.dataLayer.api.RankAPIService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { get<Retrofit>().create(LinkAPIService::class.java) }

    single { get<Retrofit>().create(AlarmAPIService::class.java) }

    single { get<Retrofit>().create(MemberAPIService::class.java)}

    single { get<Retrofit>().create(RankAPIService::class.java) }

}