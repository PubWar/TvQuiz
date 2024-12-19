package com.pubwar.quiz.io.network

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.io.dto.QuizConfResponseDto

interface RemoteDataSource {
    suspend fun getQuizConfiguration(quizId: String): Result<String, DataError.Remote>

    suspend fun login(username: String, password: String) : Result<Boolean, DataError.Remote>
    suspend fun encrypt(plainString: String) : Result<String, DataError.Remote>
    suspend fun decrypt(encryptedHexString: String) : Result<String, DataError.Remote>
}