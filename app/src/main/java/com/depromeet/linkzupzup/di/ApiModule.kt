package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.dataSources.api.LinkAPIService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { get<Retrofit>().create(LinkAPIService::class.java) }

}