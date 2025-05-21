package com.kingmakers.codemotion25kmp.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoinAndroid(
    modules: List<Module>,
    appDeclaration: KoinAppDeclaration = {}
): KoinApplication {
    val platformModule = module {}
    return initKoin(
        platformModule,
        modules,
        appDeclaration,
    )
}