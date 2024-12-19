package com.pubwar.quiz.domain.repos

import pubwartvquiz.composeapp.generated.resources.Res

interface ActiveQuizRepo {
    suspend fun getQuizId() : String
    suspend fun getExpiredTime(quizId: String) : Array<Long>

}