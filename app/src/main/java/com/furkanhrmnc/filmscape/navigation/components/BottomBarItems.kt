package com.furkanhrmnc.filmscape.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom Navigasyon Bar'ı oluşturan yapının içeriğini bu sınıf oluşturuyor.
 *
 * @param title Adı
 * @param selectedIcon Seçili yerdeki ikonu
 * @param unselectedIcon Henüz seçilmemişken veya seçim durumu kendinde değilen ikon
 * @param route Navigasyon için rotası
 *
 * @author Furkan Harmancı
 */
sealed class BottomBarItems(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
) {
    data object Home : BottomBarItems(
        title = "Home",
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        route = Destinations.MAIN.route
    )

    data object Search : BottomBarItems(
        title = "Search",
        selectedIcon = Icons.Outlined.Search,
        unselectedIcon = Icons.Filled.Search,
        route = Destinations.SEARCH.route
    )

    data object POPULAR : BottomBarItems(
        title = "Popular",
        selectedIcon = Icons.Outlined.LocalFireDepartment,
        unselectedIcon = Icons.Filled.LocalFireDepartment,
        route = Destinations.POPULAR.route
    )

    data object Favorite : BottomBarItems(
        title = "Favorite",
        selectedIcon = Icons.Outlined.Favorite,
        unselectedIcon = Icons.Filled.Favorite,
        route = Destinations.FAVORITE.route
    )
}