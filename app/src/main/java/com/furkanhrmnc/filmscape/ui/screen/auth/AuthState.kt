package com.furkanhrmnc.filmscape.ui.screen.auth

data class AuthState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: Throwable? = null,
)