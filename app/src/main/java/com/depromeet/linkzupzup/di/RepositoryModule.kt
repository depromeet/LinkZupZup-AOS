package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepositoryImpl() }
}