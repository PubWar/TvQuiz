package com.pubwar.quiz.io.network

import com.pubwar.quiz.BuildKonfig.BASE_URL
import com.pubwar.quiz.core.data.safeCall
import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.domain.model.User
import com.pubwar.quiz.io.dto.QuizConfResponseDto
import com.pubwar.quiz.utills.decrypt
import com.pubwar.quiz.utills.decryptString
import com.pubwar.quiz.utills.encrypt
import com.pubwar.quiz.utills.phoneFormat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parametersOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun getQuizConfiguration(quizId: String): Result<String, DataError.Remote> {
        val response = httpClient.post(
            urlString = "$BASE_URL/api/Configuration/get_configuration-quiz/"
        ){
            parameter("quizId", quizId)
        }
        return Result.Success(response.body<String>())
    }

    override suspend fun login(
        phoneNumber: String,
    ): Result<User, DataError.Remote> {

        return safeCall<User> {
            httpClient.post(
                urlString = "$BASE_URL/api/Auth/login_user",
            ) {
                parameter("phoneNumberEncrypted", phoneNumber.phoneFormat().encrypt())
            }
        }
    }

    override suspend fun register(
        firstname: String,
        lastname: String,
        phoneNumber: String,
        email: String,
        city: String,
        age: Int,
    ): Result<String, DataError.Remote> {

        val user = User(
            firstName = firstname,
            lastName = lastname,
            email = email,
            city = city,
            age = age,
            phoneNumber = phoneNumber.phoneFormat()
        )

        return safeCall<String> {
            httpClient.post(
                urlString = "$BASE_URL/api/Auth/register_user",
            ) {
                parameter("userDataEncrypted", Json.encodeToString(user).encrypt())
            }
        }
    }

    override suspend fun sendResult(
        userId: String,
        quizId: String,
        gameId: Int,
        points: Int,
        answerInSecond: Int
    ): Result<String, DataError.Remote> {

        val params = "{\n" +
                "  \"userId\": \"${userId}\",\n" +
                "  \"quizId\": \"${quizId}\",\n" +
                "  \"gameId\": $gameId ,\n" +
                "  \"points\": $points ,\n" +
                "  \"answerInSecond\": $answerInSecond \n" +
                "}\n"

        return safeCall<String> {
            httpClient.post(
                urlString = "$BASE_URL/api/Results/send_result",
            ) {
                parameter("results", params.encrypt())
            }
        }
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