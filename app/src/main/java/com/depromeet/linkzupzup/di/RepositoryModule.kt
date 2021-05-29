package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.domains.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.domains.repositories.LinkRepositoryImpl
import com.depromeet.linkzupzup.domains.repositories.TagRepositoryImpl
import com.depromeet.linkzupzup.domains.repositories.UserRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepositoryImpl() }
    factory { TagRepositoryImpl() }
    factory { AlarmRepositoryImpl() }
    factory { LinkRepositoryImpl(get(), get()) }
}