package com.furkanhrmnc.filmscape.ui.screen.main

sealed class MainUiEvent {
    data class Paginate(val type : String): MainUiEvent()
    data class Refresh(val type : String): MainUiEvent()
}
