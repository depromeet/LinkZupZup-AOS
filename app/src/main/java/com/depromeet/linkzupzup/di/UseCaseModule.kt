package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.domains.AlarmUseCases
import com.depromeet.linkzupzup.domains.ScrapUseCases
import com.depromeet.linkzupzup.domains.UserUseCases
import org.koin.dsl.module

val useCaseModule = module {
    factory { UserUseCases(get()) }
    factory { ScrapUseCases(get()) }
    factory { AlarmUseCases(get()) }
}