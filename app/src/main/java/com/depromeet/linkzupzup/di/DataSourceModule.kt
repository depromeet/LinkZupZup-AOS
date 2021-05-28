package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.dataSources.LinkDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { LinkDataSource(get()) }
}