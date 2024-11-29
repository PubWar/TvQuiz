package com.pubwar.quiz.di

import com.pubwar.quiz.getPlatformDataManager
import org.koin.dsl.module
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val platformModule = module{
    single<HttpClientEngine> { Darwin.create() }
    single { getPlatformDataManager(null) }
}
