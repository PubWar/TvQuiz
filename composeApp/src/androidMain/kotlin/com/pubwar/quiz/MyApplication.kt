package com.pubwar.quiz

import android.app.Application
import com.pubwar.quiz.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin{
            androidContext(this@MyApplication)
        }

    }

}