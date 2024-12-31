package com.pubwar.quiz.io.network

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.io.dto.QuizConfResponseDto

interface RemoteDataSource {
    suspend fun getQuizConfiguration(quizId: String): Result<String, DataError.Remote>

    suspend fun login(phoneNumber: String) : Result<String, DataError.Remote>
    suspend fun register(firstname: String, lastname: String, phoneNumber: String) : Result<String, DataError.Remote>
    suspend fun sendResult(userId: String, quizId: String, gameId: Int, points: Int, answerInSecond: Int) : Result<String, DataError.Remote>
    suspend fun encrypt(plainString: String) : Result<String, DataError.Remote>
    suspend fun decrypt(encryptedHexString: String) : Result<String, DataError.Remote>
}