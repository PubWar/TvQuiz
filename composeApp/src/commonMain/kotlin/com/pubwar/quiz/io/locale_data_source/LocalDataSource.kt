package com.pubwar.quiz.io.locale_data_source

interface LocalDataSource {
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): String

    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): String?


    suspend fun saveCurrentQuizId(id: String)
    suspend fun getCurrentQuizId(): String
    suspend fun saveCurrentQuizStartTime(startTime: Long)
    suspend fun getCurrentQuizStartTime(): Long

    suspend fun saveCurrentQuizExpiration(expiration: Long)
    suspend fun getCurrentQuizExpiration(): Long

    suspend fun saveCurrentQuizConfiguration(configuration: String, quizId: String)
    suspend fun getCurrentQuizConfiguration(quizId: String): String?

    suspend fun clearActiveQuiz()

}