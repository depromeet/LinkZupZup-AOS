package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.domainLayer.AlarmUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.LinkUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.ScrapUseCases
import com.depromeet.linkzupzup.architecture.domainLayer.UserUseCases
import org.koin.dsl.module

val useCaseModule = module {
    factory { UserUseCases(get()) }
    factory { LinkUseCases(get()) }
    factory { AlarmUseCases(get()) }
    factory { ScrapUseCases(get()) }
}