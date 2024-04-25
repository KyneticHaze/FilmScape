package com.furkanhrmnc.filmscape.navigation

sealed class Routes(val route : String) {
    data object Home : Routes("home_screen")
    data object Movies: Routes("movies_screen")
    data object Detail : Routes("detail_screen")
    data object Actors: Routes("actors_screen")
    data object Favorites: Routes("favorites_screen")
}