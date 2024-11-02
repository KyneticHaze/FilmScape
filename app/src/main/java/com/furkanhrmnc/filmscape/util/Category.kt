package com.furkanhrmnc.filmscape.util

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

    companion object {

        fun getCategoryByName(name: String?): Category {
            return entries.firstOrNull { it.name == name } ?: POPULAR
        }
    }
}