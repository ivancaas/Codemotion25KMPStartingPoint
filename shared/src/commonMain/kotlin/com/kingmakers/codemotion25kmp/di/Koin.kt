package com.kingmakers.codemotion25kmp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

internal fun initKoin(
    platformModule: Module,
    modules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {},
): KoinApplication {
    return startKoin {
        appDeclaration()
        val commonModule = module {
        }
        if (modules.isNotEmpty())
            modules(modules)
        modules(commonModule, platformModule)
    }
}