package com.pubwar.quiz.io.repository

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.core.domain.map
import com.pubwar.quiz.domain.model.Game
import com.pubwar.quiz.domain.repos.QuizRepository
import com.pubwar.quiz.io.network.RemoteDataSource

class QuizRepoImpl(
    private val remoteDataSource: RemoteDataSource,
) : QuizRepository {
    override suspend fun getQuiz(quizId: String): Result<List<Game>, DataError.Remote> {

        return remoteDataSource
            .getQuizConfiguration(quizId)
            .map { dto ->
                dto.games.map { it }
            }
    }

}