package com.pubwar.quiz.domain.repos

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.domain.model.Game


interface QuizRepository {
    suspend fun getQuiz(quizId: String) : Result<List<Game> , DataError.Remote>
    suspend fun setCurrentQuiz(quizId: String, startTime: Long, expired: Long)

    suspend fun sendResult(gameId: Int, points: Int, answerInSecond: Int): Result<String, DataError.Remote>
}