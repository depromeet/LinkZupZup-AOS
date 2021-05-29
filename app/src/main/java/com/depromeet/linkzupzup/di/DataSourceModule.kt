package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.LinkDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { LinkDataSource(get()) }
}