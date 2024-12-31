package com.pubwar.quiz.io.repository

import com.pubwar.quiz.domain.repos.ActiveQuizRepo
import com.pubwar.quiz.getCurrentTime
import com.pubwar.quiz.io.dto.QuizConfResponseDto
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.utills.decrypt

class ActiveQuizRepoImpl(private val localDataSource: LocalDataSource) : ActiveQuizRepo{
    override suspend fun getQuizId() : String{
       return localDataSource.getCurrentQuizId()
    }

    override suspend fun getExpiredTime(quizId: String) : Array<Long>{
        val configuration = localDataSource.getCurrentQuizConfiguration(quizId)
        val expiration = localDataSource.getCurrentQuizExpiration()
        val startTime = localDataSource.getCurrentQuizStartTime()
        val currentTime  = getCurrentTime()
        val lastGame = configuration?.decrypt<QuizConfResponseDto>()?.games?.last()

        val expired = (currentTime - startTime) + expiration

        if (lastGame != null) {
            return if(lastGame.end - expired / 1000 > 0)
            {

                arrayOf(expiration, startTime)
            }

            else{
                localDataSource.clearActiveQuiz()
                arrayOf(0,0)
            }
        }
        return  arrayOf(0,0)
    }
}