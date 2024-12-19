package com.pubwar.quiz.di

import com.pubwar.quiz.getPlatformDataManager
import com.pubwar.quiz.getSpeechToTextManager
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { getPlatformDataManager(androidContext()) }
    single { getSpeechToTextManager(androidContext()) }
}
