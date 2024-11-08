package com.furkanhrmnc.filmscape.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserSignUpViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _registerState = MutableStateFlow(AuthState())
    val registerState: StateFlow<AuthState> = _registerState.asStateFlow()

    fun registerUser(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _registerState.update { it.copy(isLoading = true) }
        authRepository.registerUser(email, password).collectLatest { response ->
            _registerState.update {
                when (response) {
                    is Response.Failure -> it.copy(error = response.throwable, isLoading = false)
                    is Response.Success -> it.copy(
                        success = "Register Successful!",
                        isLoading = false
                    )
                }
            }
        }
    }
}

