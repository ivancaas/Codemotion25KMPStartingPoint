package com.kingmakers.codemotion25kmp.data

import com.kingmakers.codemotion25kmp.Engine
import com.kingmakers.codemotion25kmp.configurePlatform
import com.kingmakers.codemotion25kmp.data.api.ExampleApi
import com.kingmakers.codemotion25kmp.data.api.createExampleApi
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

fun dataModule() = module {
    single { ktorfitBuilder() }
    single { createAuthApi(get()) }
}

fun createAuthApi(ktorfit: Ktorfit): ExampleApi {
    return ktorfit.createExampleApi()
}

fun ktorfitBuilder(
): Ktorfit {
    return Ktorfit.Builder().httpClient(
        httpClient()
    ).converterFactories(ResponseConverterFactory()).build()
}

fun httpClient() =
    HttpClient(Engine) {

        configurePlatform()

        install(HttpTimeout) {
            connectTimeoutMillis = 60.seconds.inWholeMilliseconds
            requestTimeoutMillis = 60.seconds.inWholeMilliseconds
            socketTimeoutMillis = 2.minutes.inWholeMilliseconds
        }

        install(ContentNegotiation) {
            json(
                contentType = ContentType.Any,
                json =
                Json {
                    isLenient = false
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    classDiscriminator = "type"
                    encodeDefaults = true
                    explicitNulls = false
                },
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
        }
    }