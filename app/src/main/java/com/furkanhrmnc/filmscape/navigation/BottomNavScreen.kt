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
import com.furkanhrmnc.filmscape.R

sealed class BottomNavScreen(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val labelResId: Int,
) {
    data object Favorites : BottomNavScreen(
        Destinations.FAVORITE.route,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
        R.string.favorites_label
    )

    data object Search : BottomNavScreen(
        Destinations.SEARCH.route,
        Icons.Filled.Search,
        Icons.Outlined.Search,
        R.string.search_label
    )

    data object Person : BottomNavScreen(
        Destinations.PERSON.route,
        Icons.Filled.Person,
        Icons.Outlined.Person,
        R.string.person_label
    )

    data object Account : BottomNavScreen(
        Destinations.ACCOUNT.route,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        R.string.account_label
    )
}