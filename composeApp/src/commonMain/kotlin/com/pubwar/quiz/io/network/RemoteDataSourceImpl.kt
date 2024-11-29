package com.pubwar.quiz.io.network

import com.pubwar.quiz.core.data.safeCall
import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.io.dto.QuizConfResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = ""

class RemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun getQuizConfiguration(quizId: String): Result<QuizConfResponseDto, DataError.Remote> {

        val response = httpClient.get(
            urlString = "https://srdjankokot.github.io/resume/emisija.json"
        )

        return safeCall<QuizConfResponseDto> {
          response.body()
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): Result<Boolean, DataError.Remote> {
        kotlinx.coroutines.delay(2000)

        return if (username == "user" && password == "password")
            Result.Success(true)
        else
            Result.Error(DataError.Remote.UNKNOWN)
    }

    override suspend fun encrypt(plainString: String): Result<String, DataError.Remote> {
        return safeCall<String> {
            httpClient.get(
                urlString = "$BASE_URL/api/Encrypt/$plainString"
            )
        }
    }

    override suspend fun decrypt(encryptedHexString: String): Result<String, DataError.Remote> {
        return safeCall<String> {
            httpClient.get(
                urlString = "$BASE_URL/api/Decrypt/$encryptedHexString"
            )
        }
    }


}