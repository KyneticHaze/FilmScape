package com.furkanhrmnc.filmscape.navigation

sealed class Routes(val route : String) {
    data object Home : Routes("home_screen")
    data object Detail : Routes("detail_screen")
}