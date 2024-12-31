package com.pubwar.quiz


import androidx.compose.ui.window.ComposeUIViewController
import com.pubwar.quiz.di.initKoin

fun MainViewController() = ComposeUIViewController(

    configure = {
        initKoin()
    }
) {
    App()
}