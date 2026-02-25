package com.pubwar.quiz

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.pubwar.quiz.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)


        if (BuildKonfig.DEBUG) {
            println("it is  debug mode")
            val appCheck = FirebaseAppCheck.getInstance()
            appCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
        } else {
            println("it is not debug mode")
        }

        initKoin {
            androidContext(this@MyApplication)
        }

    }

}