package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.AlarmDataSource
import com.depromeet.linkzupzup.architecture.dataLayer.LinkDataSource
import com.depromeet.linkzupzup.architecture.dataLayer.MemberDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { LinkDataSource(get()) }
    factory { AlarmDataSource(get()) }
    factory { MemberDataSource(get()) }
}