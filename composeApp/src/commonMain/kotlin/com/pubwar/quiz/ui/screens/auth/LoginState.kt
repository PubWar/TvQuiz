package com.pubwar.quiz.ui.screens.auth


enum class PhoneAuthState{
    EnterNumber,
    EnterCode,
    PhoneIsVerified,
    Unauthorized
}
data class LoginState(
    val showLoginPage: Boolean = false,
    val phoneNumber: String = "+381",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val address: String = "",
    val age: String = "",
    val smsCode: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false,

    val verificationId: String = "",
    val phoneAuthState: PhoneAuthState = PhoneAuthState.EnterNumber,
    val verifiedPhoneNumber: String = "",

)
