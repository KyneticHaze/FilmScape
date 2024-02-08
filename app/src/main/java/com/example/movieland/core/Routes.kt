package com.example.movieland.core

sealed class Routes(val route : String) {
    data object Splash : Routes("splash_screen")
    data object Main : Routes("main_screen")
    data object Detail : Routes("detail_screen")
    data object Search : Routes("search_screen")
}