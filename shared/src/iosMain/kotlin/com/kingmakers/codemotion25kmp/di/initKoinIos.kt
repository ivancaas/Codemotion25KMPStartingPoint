package com.kingmakers.codemotion25kmp.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("unused") // Called from Swift
fun initKoinIos(
    modules: List<Module> = emptyList(),
): KoinApplication {
    val plarformModule = module {
    }
    return initKoin(
        plarformModule,
        modules,
    )
}