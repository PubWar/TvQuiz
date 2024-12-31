package com.pubwar.quiz

import android.app.Application
import com.google.firebase.FirebaseApp
import com.pubwar.quiz.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        initKoin{
            androidContext(this@MyApplication)
        }

    }

}