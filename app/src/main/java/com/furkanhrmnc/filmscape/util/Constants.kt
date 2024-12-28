package com.furkanhrmnc.filmscape.util

import androidx.compose.ui.graphics.Color
import com.furkanhrmnc.filmscape.domain.model.Media


object Constants {
    const val PER_PAGE_COUNT = 20

    // Theme DataStore
    const val IS_DYNAMIC_THEME = "is_dynamic_theme"
    const val THEME_TYPE = "theme_type"

    // Language
    const val ENGLISH = "en"
    const val TURKISH = "tr"


    // Firebase Firestore
    const val MEDIA = "media"
    const val FAVORITES = "favorites"

    const val UNKNOWN_ERROR = "Unknown Error"

    const val SUBSCRIBED_MS = 5000L
    const val CAROUSEL_MS = 4000L
    const val IMAGE_DURATION_MS = 1000
    const val SHIMMER_DURATION_MS = 1000

    const val TYPE_TRAILER = "Trailer"
    const val TYPE_FEATURETTE = "Featurette"
    const val YOUTUBE = "Youtube"

    val STAR_COLOR = Color(0xF2CDDB0E)

    const val SHIMMER_INFINITE_LABEL = "Shimmer Effect Infinite Transition"
    const val SHIMMER_ANIMATION_LABEL = "Shimmer Animation"

    private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    const val IMAGE_STORAGE_PATH = "image/*"

    fun imageFormatter(path: String?): String =
        if (path.isNullOrEmpty()) "" else "${IMAGE_URL}$path"

    fun getDisplayName(media: Media): String {
        return when {
            !media.title.isNullOrEmpty() -> media.title
            !media.name.isNullOrEmpty() -> media.name
            !media.originalTitle.isNullOrEmpty() -> media.originalTitle
            !media.originalName.isNullOrEmpty() -> media.originalName
            else -> "No Title Available"
        }
    }
}