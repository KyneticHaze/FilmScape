package com.furkanhrmnc.filmscape.ui.screen.auth.login

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.ui.screen.auth.AuthUiState
import com.furkanhrmnc.filmscape.util.Response
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

open class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    open val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    var splashScreen = mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            delay(2.seconds)
            splashScreen.value = false
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        if (email.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Please enter email and password") }
            return@launch
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid email address") }
            return@launch
        }

        viewModelScope.launch {
            authRepository.loginUser(email, password)
                .collect { response ->
                    _uiState.update {
                        when (response) {
                            is Response.Failure -> {
                                val errorMessage = when (response.throwable) {
                                    is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                                    else -> response.throwable.message ?: "An error occurred"
                                }
                                it.copy(errorMessage = errorMessage)
                            }

                            is Response.Success -> it.copy(
                                successMessage = "Login Successful!",
                                errorMessage = null
                            )
                        }
                    }
                }
        }
    }

    fun clearSuccessMessage() {
        _uiState.update { it.copy(successMessage = "") }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun updateEmail(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun updateFocusedState(isFocused: Boolean) {
        _uiState.update { it.copy(isFocused = isFocused) }
    }
}