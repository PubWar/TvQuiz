package com.pubwar.quiz.ui.screens.intro

data class IntroState(
    val username: String = "",
    val activeQuiz: Boolean = false,
    val activeQuizExpired: Int = 0,
    val activeQuizStarted: Long = 0
)

