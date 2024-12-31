package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.core.domain.DataError
import com.pubwar.quiz.core.domain.onError
import com.pubwar.quiz.core.domain.onSuccess
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.ui.screens.auth.LoginState
import com.pubwar.quiz.ui.screens.auth.PhoneAuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state

    init {
        viewModelScope.launch {
            loginRepo.isLoggedIn().let { loggedIn ->
                if (loggedIn) {
                    _state.update {
                        it.copy(
                            success = true
                        )
                    }
                }

            }
        }
    }


    fun onPhoneNumberChanged(phoneNumber: String) {
        _state.update {
            it.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    fun onSmsCodeChanged(smsCode: String) {
        _state.update {
            it.copy(
                smsCode = smsCode
            )
        }
    }

    fun onFirstChanged(firstname: String) {
        _state.update {
            it.copy(
                firstName = firstname
            )
        }
    }

    fun onLastnameChanged(lastname: String) {
        _state.update {
            it.copy(
                lastName = lastname
            )
        }
    }


    fun verifyCode() = viewModelScope.launch {

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        loginRepo.verifyCode(
            _state.value.verificationId,
            _state.value.smsCode,
            success = { phoneNumber ->
                login(phoneNumber)
            })
    }

    fun verifyPhoneNumber() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        loginRepo.verifyPhoneNumber(_state.value.phoneNumber, onCodeSent = { verificationId ->
            _state.update {
                it.copy(
                    verificationId = verificationId,
                    phoneAuthState = PhoneAuthState.EnterCode,
                    isLoading = false,
                    errorMessage = null
                )
            }
        },
            onError =
            {error ->
                _state.update {
                    it.copy(
                        verificationId = "",
                        phoneAuthState = PhoneAuthState.EnterNumber,
                        isLoading = false,
                        errorMessage = error
                    )
                }
            }
        )
    }

    private fun login(phoneNumber: String) = viewModelScope.launch {

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        loginRepo.login(phoneNumber)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        verifiedPhoneNumber = response,
                        errorMessage = null,
                        isLoading = false,
                        success = true,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = if (error == DataError.Remote.UNAUTHORIZED) null else error.name,
                        phoneAuthState = if (error == DataError.Remote.UNAUTHORIZED) PhoneAuthState.Unauthorized else it.phoneAuthState
                    )
                }
            }
    }

    fun register(firstname: String, lastname: String) = viewModelScope.launch {
        loginRepo.register(firstname, lastname, _state.value.phoneNumber)
            .onSuccess {
                _state.update {
                    it.copy(
                        verifiedPhoneNumber = _state.value.phoneNumber,
                        errorMessage = null,
                        isLoading = false,
                        success = true,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.name,
                    )
                }
            }
    }
}

