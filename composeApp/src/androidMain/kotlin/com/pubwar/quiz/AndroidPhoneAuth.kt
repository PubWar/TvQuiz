package com.pubwar.quiz

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.pubwar.quiz.auth.PhoneAuth
import java.util.concurrent.TimeUnit
class AndroidPhoneAuth : PhoneAuth {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authVerify = FirebaseAuth.getInstance().initializeRecaptchaConfig()
    override fun verifyPhoneNumber(
        phoneNumber: String,
        onCodeSent: (String) -> Unit,
        onVerificationFailed: (Exception) -> Unit
    ) {
        val activity = ActivityProvider.getActivity()
            ?: throw IllegalStateException("Activity is not available")



        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-retrieval or instant verification
                    println("onVerificationCompleted")
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onVerificationFailed(e)
                    println("onVerificationFailed")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    onCodeSent(verificationId)
                    println("onCodeSent")
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyCode(
        verificationId: String,
        code: String,
        onSuccess: (phoneNumber: String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    task.result.user?.phoneNumber?.let { onSuccess(it) }
                } else {
                    onError(task.exception ?: Exception("Unknown error"))
                }
            }
    }
}