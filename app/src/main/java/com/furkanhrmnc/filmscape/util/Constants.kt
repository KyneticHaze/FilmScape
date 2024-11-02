package com.furkanhrmnc.filmscape.util


object Constants {
    const val PER_PAGE_COUNT = 20
    const val SNACK_LABEL_OK = "Ok, I understand"

    const val MEDIA_TABLE = "media_entity"
    const val MEDIA_DB = "media_db"
    const val UNKNOWN_ERROR = "Unknown Error"

    const val SUBSCRIBED_MS = 5000L
    const val IMAGE_DURATION_MS = 1000

    const val TYPE_TRAILER = "Trailer"

    const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

    fun imageFormatter(path: String?): String = if (path == null) "" else "${IMAGE_URL}$path"
}