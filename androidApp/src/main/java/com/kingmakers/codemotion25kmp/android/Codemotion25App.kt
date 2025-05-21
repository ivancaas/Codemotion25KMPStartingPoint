package com.kingmakers.codemotion25kmp.android

import android.app.Application
import com.kingmakers.codemotion25kmp.di.initKoinAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class Codemotion25App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(
            emptyList()
        ) {
            androidLogger(Level.ERROR)
            androidContext(this@Codemotion25App)
        }
    }
}