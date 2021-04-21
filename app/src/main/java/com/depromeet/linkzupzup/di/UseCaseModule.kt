package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.domains.UserUseCases
import org.koin.dsl.module

val useCaseModule = module {
    single { UserUseCases(get()) }
}