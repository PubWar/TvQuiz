package com.pubwar.quiz.domain.repos

import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.domain.model.User

interface LoginRepository {
    suspend fun login(phoneNumber: String): Result<String, DataError.Remote>
    suspend fun register(firstname: String, lastname: String, phoneNumber: String,): Result<String, DataError.Remote>
    suspend fun isLoggedIn(): Boolean

    suspend fun verifyPhoneNumber(phoneNumber: String, onCodeSent: (verificationId: String) -> Unit,  onError: (error: String) -> Unit)
    suspend fun verifyCode(verificationId: String, code: String, success: (verifiedPhoneNumber: String) -> Unit)
}