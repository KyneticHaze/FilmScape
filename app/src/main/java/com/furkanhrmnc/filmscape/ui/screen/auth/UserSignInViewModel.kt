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

class UserSignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow(AuthState())
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    fun signInUser(email: String, password: String) = viewModelScope.launch {
        _loginState.update { it.copy(isLoading = true) }
        authRepository.loginUser(email, password).collectLatest { response ->
            _loginState.update {
                when (response) {
                    is Response.Failure -> it.copy(error = response.throwable, isLoading = false)
                    is Response.Success -> it.copy(success = "Login Successful!", isLoading = false)
                }
            }
        }
    }
}