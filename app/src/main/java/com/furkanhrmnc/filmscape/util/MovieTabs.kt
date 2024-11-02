package com.furkanhrmnc.filmscape.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector

enum class MovieTabs(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val category: String,
) {
    Popular(
        selectedIcon = Icons.Filled.LocalFireDepartment,
        unSelectedIcon = Icons.Outlined.LocalFireDepartment,
        category = "Popular",
    ),
    TopRated(
        selectedIcon = Icons.Filled.Star,
        unSelectedIcon = Icons.Outlined.StarBorder,
        category = "Top Rated",
    ),
    Upcoming(
        selectedIcon = Icons.Filled.Upcoming,
        unSelectedIcon = Icons.Outlined.Upcoming,
        category = "Upcoming",
    ),
    NowPlaying(
        selectedIcon = Icons.Filled.PlayCircle,
        unSelectedIcon = Icons.Outlined.PlayCircle,
        category = "Now Playing",
    )
}