package com.pubwar.quiz.domain.repos

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Boolean, DataError.Remote>
}