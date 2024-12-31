package com.pubwar.quiz.ui.screens.auth


enum class PhoneAuthState{
    EnterNumber,
    EnterCode,
    PhoneIsVerified,
    Unauthorized
}
data class LoginState(
    val phoneNumber: String = "+381631322248",
    val firstName: String = "",
    val lastName: String = "",
    val smsCode: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false,

    val verificationId: String = "",
    val phoneAuthState: PhoneAuthState = PhoneAuthState.EnterNumber,
    val verifiedPhoneNumber: String = "",

)
