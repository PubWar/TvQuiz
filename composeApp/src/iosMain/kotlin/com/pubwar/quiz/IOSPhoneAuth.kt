package com.pubwar.quiz

import com.pubwar.quiz.auth.PhoneAuth
import cocoapods.FirebaseAuth.*
import cocoapods.FirebaseCore.*
import kotlinx.cinterop.ExperimentalForeignApi

class IOSPhoneAuth : PhoneAuth {
    @OptIn(ExperimentalForeignApi::class)
    override fun verifyPhoneNumber(
        phoneNumber: String,
        onCodeSent: (String) -> Unit,
        onVerificationFailed: (Exception) -> Unit
    ) {

        println("=============")
        println(phoneNumber)
        println("=============")

        FIRPhoneAuthProvider.provider().verifyPhoneNumber(
            phoneNumber,
            null
        ) { verificationId, error ->
            if (error != null) {
                onVerificationFailed(Exception(error.localizedDescription))
            } else {
                onCodeSent(verificationId ?: "")
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun verifyCode(
        verificationId: String,
        code: String,
        onSuccess: (phoneNumber: String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val credential = FIRPhoneAuthProvider.provider().credentialWithVerificationID(verificationId, code)
        FIRAuth.auth()?.signInWithCredential(credential) { _, error ->
            if (error != null) {
                onError(Exception(error.localizedDescription))
            } else {
                onSuccess("need to implement")
            }
        }
    }
}