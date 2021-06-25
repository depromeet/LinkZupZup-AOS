package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.api.*
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { get<Retrofit>().create(LinkAPIService::class.java) }

    single { get<Retrofit>().create(AlarmAPIService::class.java) }

    single { get<Retrofit>().create(MemberAPIService::class.java)}

    single { get<Retrofit>().create(RankAPIService::class.java) }

}