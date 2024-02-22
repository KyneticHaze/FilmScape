package com.furkanhrmnc.filmscape.ui.screens.main

sealed class MainUiEvent {
    data class Paginate(val type : String): MainUiEvent()
    data class Refresh(val type : String): MainUiEvent()
}
