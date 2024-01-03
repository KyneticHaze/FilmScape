package com.furkanharmanc.movieland.core

sealed class Routes(val route : String) {
    data object Home : Routes("home_screen")
    data object Latest : Routes("latest_screen")
    data object Rated : Routes("rated_screen")
    data object Favorite : Routes("favorite_screen")
    data object Account : Routes("account_screen")
    data object Detail : Routes("detail_screen")
}
