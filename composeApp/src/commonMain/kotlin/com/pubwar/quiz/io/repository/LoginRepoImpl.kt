package com.pubwar.quiz.io.repository

import com.pubwar.quiz.auth.FCMToken
import com.pubwar.quiz.auth.PhoneAuth
import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.Result
import com.pubwar.quiz.core.domain.map
import com.pubwar.quiz.domain.model.User
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.io.locale_data_source.LocalDataSource
import com.pubwar.quiz.io.network.RemoteDataSource
import com.pubwar.quiz.utills.decrypt
import com.pubwar.quiz.utills.decryptString

class LoginRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val phoneAuth: PhoneAuth,
    private val fcmToken: FCMToken
) : LoginRepository {

    override suspend fun login(phoneNumber: String): Result<String, DataError.Remote> {
//        localDataSource.saveUsername(username)
        return remoteDataSource
            .login(phoneNumber).map {
                localDataSource.saveAccessToken(it)
                it
            }
    }

    override suspend fun register(
        firstname: String,
        lastname: String,
        phoneNumber: String,
    ): Result<String, DataError.Remote> {


        return remoteDataSource.register(firstname, lastname, phoneNumber).map {
            localDataSource.saveAccessToken(it)
            localDataSource.saveUsername(firstname)
            it
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return !localDataSource.getAccessToken().isNullOrEmpty()
    }

    override suspend fun verifyPhoneNumber(
        phoneNumber: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (error: String) -> Unit
    ) {

//        fcmToken.getToken(
//            token = {
//                println("FCM Token: $it")
//            },
//            error = {
//                println(it)
//            }
//        )

        phoneAuth.verifyPhoneNumber(
            phoneNumber,
            onCodeSent = { verificationId ->
                println("Code sent with verificationId: $verificationId")
                onCodeSent(verificationId)
            },
            onVerificationFailed = { e ->
                println("Verification failed: ${e.message}")
                onError("Verification failed: ${e.message}")
            }
        )
    }

    override suspend fun verifyCode(
        verificationId: String,
        code: String,
        success: (verifiedPhoneNumber: String) -> Unit
    ) {
        phoneAuth.verifyCode(
            verificationId,
            code,
            onSuccess = {
                println("Phone verified successfully!")
                success(it)
            },
            onError = { e ->
                println("Verification failed: ${e.message}")
            }
        )
    }

}