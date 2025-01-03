package com.furkanhrmnc.filmscape.util

sealed class UiEvent {
    data class NavigateTo(val route: String) : UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null,
    ) : UiEvent()

    data class Toast(val toastMessage: String) : UiEvent()
}
