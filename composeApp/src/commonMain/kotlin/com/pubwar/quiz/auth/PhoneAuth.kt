package com.pubwar.quiz.auth

interface PhoneAuth {
    fun verifyPhoneNumber(phoneNumber: String, onCodeSent: (String) -> Unit, onVerificationFailed: (Exception) -> Unit)
    fun verifyCode(verificationId: String, code: String, onSuccess: (phoneNumber: String) -> Unit, onError: (Exception) -> Unit)
}