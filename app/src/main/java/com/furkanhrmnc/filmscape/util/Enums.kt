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
import com.furkanhrmnc.filmscape.R

/**
 * Bu sınıf, kategorilere göre film çekme isteğini daha basit işlemek için yazıldı.
 *
 * @author Furkan Harmancı
 */
enum class Category {
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING;
}

/**
 * Bu sınıf, media türlerine göre media çekme isteğini daha basit işlemek için yazıldı.
 * @author Furkan Harmancı
 */
enum class MediaType(val lowerName: String) {
    MOVIE("movie"),
    TV("tv")
}

/**
 * Bu sınıf, Film tablerini göstermek için yazıldı
 * @author Furkan Harmancı
 */
enum class MovieTabs(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val categoryResId: Int,
) {
    Popular(
        selectedIcon = Icons.Filled.LocalFireDepartment,
        unSelectedIcon = Icons.Outlined.LocalFireDepartment,
        categoryResId = R.string.popular,
    ),
    TopRated(
        selectedIcon = Icons.Filled.Star,
        unSelectedIcon = Icons.Outlined.StarBorder,
        categoryResId = R.string.top_rated,
    ),
    Upcoming(
        selectedIcon = Icons.Filled.Upcoming,
        unSelectedIcon = Icons.Outlined.Upcoming,
        categoryResId = R.string.upcoming,
    ),
    NowPlaying(
        selectedIcon = Icons.Filled.PlayCircle,
        unSelectedIcon = Icons.Outlined.PlayCircle,
        categoryResId = R.string.now_playing,
    )
}

/**
 * Bu sınıf, TV tablerini göstermek için yazıldı
 * @author Furkan Harmancı
 */
enum class TvTabs(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val categoryResId: Int,
) {
    Popular(
        selectedIcon = Icons.Filled.LocalFireDepartment,
        unSelectedIcon = Icons.Outlined.LocalFireDepartment,
        categoryResId = R.string.popular,
    ),
    TopRated(
        selectedIcon = Icons.Filled.Star,
        unSelectedIcon = Icons.Outlined.StarBorder,
        categoryResId = R.string.top_rated,
    ),
}

/**
 * Bu sınıf, Trend mediaların zamanlara göre sıralanması için yazıldı
 */
enum class Time {
    DAY,
    WEEK
}

/**
 * Bu sınıf, material3 tasarımı doğru uygulayabilmek için, tema isimlerini tutmak için yazıldı
 */
enum class ThemeType {
    SYSTEM,
    LIGHT,
    DARK
}