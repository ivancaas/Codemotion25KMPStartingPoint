import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.skie)
    alias(libs.plugins.nativeCoroutines)
    alias(libs.plugins.kotlinCocoapods)
}

skie {
    analytics {
        enabled.set(false)
    }

    features {
        coroutinesInterop.set(false)

        group {
            EnumInterop.Enabled(true)
            SealedInterop.Enabled(true)
            SuspendInterop.Enabled(false)
            FlowInterop.Enabled(false)
        }
    }
    build {
        produceDistributableFramework()
    }
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                }
            }
        }
    }

    val framework = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            framework.add(this)
            baseName = "shared"
            isStatic = true
        }
    }

    cocoapods {
        ios.deploymentTarget = "16.0"

        version = "1.0.0"
        pod("FirebaseAnalytics") {
            version = "10.27.0"
            extraOpts = listOf("-compiler-option", "-fmodules")
        }
    }
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            api(libs.kmp.observableviewmodel)
            implementation(libs.kmp.nativecoroutines.core)
            implementation(libs.kmp.nativecoroutines.annotations)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktorfit.lib)
            implementation(libs.ktorfit.converters.response)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.startup)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.firebase.analytics)
        }
    }
}


android {
    namespace = "com.kingmakers.codemotion25kmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
