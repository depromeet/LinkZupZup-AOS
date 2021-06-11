package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.MetaRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepositoryImpl(get()) }
    factory { MetaRepositoryImpl(get()) }
    factory { AlarmRepositoryImpl(get()) }
    factory { LinkRepositoryImpl(get(), get()) }
}