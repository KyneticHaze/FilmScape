package com.furkanhrmnc.filmscape.ui.screen.auth.register

import android.util.Patterns
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.ui.screen.auth.AuthUiState
import com.furkanhrmnc.filmscape.util.Response
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(
        email: String,
        password: String,
    ) = viewModelScope.launch {

        if (email.isEmpty() || password.isEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Please enter email and password")
            return@launch
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value =
                _uiState.value.copy(errorMessage = "Please enter a valid email address")
            return@launch
        }

        authRepository.registerUser(email, password).collectLatest { response ->
            _uiState.update {
                when (response) {
                    is Response.Failure -> {
                        val errorMessage = when (response.throwable) {
                            is FirebaseAuthUserCollisionException -> "This Email already registered"
                            is FirebaseAuthWeakPasswordException -> "Password is too weak. Please choose a stronger one"
                            is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                            else -> response.throwable.message ?: "An error occurred"
                        }
                        it.copy(errorMessage = errorMessage)
                    }

                    is Response.Success -> it.copy(successMessage = "Register Successful!")
                }
            }
        }
    }


    private fun evaluatePasswordStrength(password: String): String {
        val length = password.length

        if (length < 8) {
            return "Weak"
        }

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return when {
            length >= 12 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar -> "Strong"
            length in 8..11 && hasUpperCase && hasLowerCase && hasDigit -> "Medium"
            else -> "Weak"
        }
    }

    private fun updatePasswordStrengthColor(strength: String): Color {
        return when (strength) {
            "Weak" -> Color.Red
            "Medium" -> Color.Yellow
            "Strong" -> Color.Green
            else -> Color.Transparent
        }
    }

    fun clearSuccessMessage() {
        _uiState.update { it.copy(successMessage = "") }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun updateFocusState(isFocused: Boolean) {
        _uiState.value = _uiState.value.copy(isFocused = isFocused)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }

        val strength = evaluatePasswordStrength(newPassword)
        val strengthColor = updatePasswordStrengthColor(strength)

        _uiState.update {
            it.copy(
                passwordStrength = strength,
                passwordStrengthColor = strengthColor
            )
        }
    }
}

