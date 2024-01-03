package com.furkanharmanc.movieland.ui.navigation

import com.furkanharmanc.movieland.R
import com.furkanharmanc.movieland.core.Routes

sealed class NavItems(
    val title : String,
    val icon : Int,
    val route : String
) {
    data object Home : NavItems(
        title = "Home",
        icon = R.drawable.main,
        route = Routes.Home.route
    )
    data object Latest : NavItems(
        title = "Latest",
        icon = R.drawable.latest,
        route = Routes.Latest.route
    )
    data object Favorite : NavItems(
        title = "Favorite",
        icon = R.drawable.fav,
        route = Routes.Favorite.route
    )
    data object Rated : NavItems(
        title = "Rated",
        icon = R.drawable.rating,
        route = Routes.Rated.route
    )
}