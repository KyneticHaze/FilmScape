package com.example.movieland.ui.navigation

sealed class Routes(val route : String) {
    data object AppStart : Routes("appStart")
    data object Auth : Routes("auth")
    data object Home : Routes("home")
}
