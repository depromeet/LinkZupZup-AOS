package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.dataSources.TestAPIService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(TestAPIService::class.java) }
}