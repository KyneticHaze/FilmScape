package com.furkanhrmnc.filmscape.ui.navigation

sealed class Routes(val route : String) {
    data object Home : Routes("home_screen")
    data object Person: Routes("person_screen")
    data object Favorites: Routes("favorites_screen")
    data object Detail : Routes("detail_screen")
    data object Search : Routes("search_screen")
}