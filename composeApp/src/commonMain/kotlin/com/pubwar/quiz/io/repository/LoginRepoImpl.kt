package com.pubwar.quiz.io.repository

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.core.domain.map
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.io.network.RemoteDataSource

class LoginRepoImpl(
    private val remoteDataSource: RemoteDataSource,
) : LoginRepository {
    override suspend fun login(username: String, password: String): Result<Boolean, DataError.Remote> {

        return remoteDataSource
            .login(username, password).map {
                it
            }
    }

}