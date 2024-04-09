package com.furkanhrmnc.filmscape.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    data object Home : NavBarItem(
        title = "Home",
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        route = Routes.Home.route
    )

    data object Person : NavBarItem(
        title = "Person",
        selectedIcon = Icons.Outlined.Person3,
        unselectedIcon = Icons.Filled.Person3,
        route = Routes.Person.route
    )

    data object Favorite : NavBarItem(
        title = "Favorite",
        selectedIcon = Icons.Outlined.Favorite,
        unselectedIcon = Icons.Filled.Favorite,
        route = Routes.Favorites.route
    )
}