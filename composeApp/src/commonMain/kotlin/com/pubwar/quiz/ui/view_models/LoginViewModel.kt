package com.pubwar.quiz.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubwar.quiz.core.domain.onError
import com.pubwar.quiz.core.domain.onSuccess
import com.pubwar.quiz.domain.repos.LoginRepository
import com.pubwar.quiz.ui.screens.auth.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state

    init {
        viewModelScope.launch {
            loginRepo.isLoggedIn().let { loggedIn ->
                if(loggedIn)
                {
                    _state.update {
                        it.copy(
                            success = true
                        )
                    }
                }

            }
        }
    }

    fun onUsernameChanged(newUsername: String) {
        _state.update {
            it.copy(
                username = newUsername
            )
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _state.update {
            it.copy(
                password = newPassword
            )
        }
    }

    fun login() = viewModelScope.launch {

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        loginRepo.login(state.value.username, state.value.password)
            .onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        success = true
                    )
                }
            }
            .onError {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Invalid username or password"
                    )
                }
            }
    }
}

