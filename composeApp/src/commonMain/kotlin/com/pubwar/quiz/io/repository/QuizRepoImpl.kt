package com.pubwar.quiz.io.repository

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.core.domain.map
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.io.dto.QuizConfResponseDto
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.io.network.RemoteDataSource
import com.pubwar.quiz.utills.decrypt

class QuizRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : QuizRepository {
    override suspend fun getQuiz(quizId: String): Result<List<Game>, DataError.Remote> {
        // Retrieve the quiz data from preferences
        val localQuiz = localDataSource.getCurrentQuizConfiguration(quizId)
             if(localQuiz!=null)
             {
                 return Result.Success(localQuiz.decrypt<QuizConfResponseDto>().games)
             }
        println("da li ovde ima neki zaostali kviz: " + localQuiz.toString())

        return remoteDataSource
            .getQuizConfiguration(quizId)
            .map { dto ->
                localDataSource.saveCurrentQuizConfiguration(dto, quizId)
                dto.decrypt<QuizConfResponseDto>().games.map { it }
            }
    }

    override suspend fun setCurrentQuiz(quizId: String, startTime: Long, expired: Long) {
        localDataSource.saveCurrentQuizId(quizId)
        localDataSource.saveCurrentQuizStartTime(startTime)
        localDataSource.saveCurrentQuizExpiration(expired)
    }

    override suspend fun sendResult(
        gameId: Int,
        points: Int,
        answerInSecond: Int
    ): Result<String, DataError.Remote> {
        localDataSource.getAccessToken()?.let {
            remoteDataSource.sendResult(it, localDataSource.getCurrentQuizId(), gameId, points, 10)

        }
        return Result.Success("Result sent")
    }


}