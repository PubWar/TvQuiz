package com.pubwar.quiz.auth

interface FCMToken {
    fun getToken(token: (String) -> Unit, error: (String) -> Unit)
}