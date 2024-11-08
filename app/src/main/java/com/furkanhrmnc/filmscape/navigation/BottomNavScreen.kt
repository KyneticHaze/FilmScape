package com.furkanhrmnc.filmscape.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
) {
    data object Favorites : BottomNavScreen(
        Destinations.FAVORITE.route,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
        "Favorites"
    )

    data object Search : BottomNavScreen(
        Destinations.SEARCH.route,
        Icons.Filled.Search,
        Icons.Outlined.Search,
        "Search"
    )

    data object Person : BottomNavScreen(
        Destinations.PERSON.route,
        Icons.Filled.Person,
        Icons.Outlined.Person,
        "Actors"
    )

    data object Account : BottomNavScreen(
        Destinations.ACCOUNT.route,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        "Account"
    )
}