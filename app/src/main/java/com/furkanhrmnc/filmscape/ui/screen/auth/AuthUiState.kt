package com.furkanhrmnc.filmscape.ui.screen.auth

import androidx.compose.ui.graphics.Color

data class AuthUiState(
    var email: String = "",
    var password: String = "",
    var passwordStrength: String = "",
    var passwordStrengthColor: Color = Color.Transparent,
    var isPasswordVisible: Boolean = false,
    var isFocused: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String = "",
)