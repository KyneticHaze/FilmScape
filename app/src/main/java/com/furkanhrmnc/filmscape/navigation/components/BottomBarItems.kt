package com.furkanhrmnc.filmscape.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Tv
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
    val route: String
) {
    data object Home : BottomBarItems(
        title = "Home",
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        route = Routes.MAIN.route
    )

    data object Actors : BottomBarItems(
        title = "Actors",
        selectedIcon = Icons.Outlined.Person3,
        unselectedIcon = Icons.Filled.Person3,
        route = Routes.ACTORS.route
    )

    data object Tv: BottomBarItems(
        title = "Tv",
        selectedIcon = Icons.Outlined.Tv,
        unselectedIcon = Icons.Filled.Tv,
        route = Routes.TV.route
    )
}