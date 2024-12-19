package com.pubwar.quiz.io.repository

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.core.domain.map
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.io.network.RemoteDataSource

class LoginRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : LoginRepository {

    override suspend fun login(username: String, password: String): Result<Boolean, DataError.Remote> {
        localDataSource.saveUsername(username)
        return remoteDataSource
            .login(username, password).map {
                it
            }
    }

    override suspend fun isLoggedIn(): Boolean {
        return localDataSource.getUsername() != ""
    }

}