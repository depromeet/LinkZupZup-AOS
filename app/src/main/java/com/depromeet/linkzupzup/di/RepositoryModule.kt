package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.dataLayer.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.TagRepositoryImpl
import com.depromeet.linkzupzup.architecture.dataLayer.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepositoryImpl(get()) }
    factory { TagRepositoryImpl() }
    factory { AlarmRepositoryImpl() }
    factory { LinkRepositoryImpl(get(), get()) }
}