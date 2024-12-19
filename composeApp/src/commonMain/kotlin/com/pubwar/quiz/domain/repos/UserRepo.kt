package com.pubwar.quiz.domain.repos

interface UserRepo {
    suspend fun getLoggedUsername()
}