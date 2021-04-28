package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.dataSources.repositories.AlarmRepositoryImpl
import com.depromeet.linkzupzup.dataSources.repositories.TagRepositoryImpl
import com.depromeet.linkzupzup.dataSources.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepositoryImpl() }
    factory { TagRepositoryImpl() }
    factory { AlarmRepositoryImpl() }
}