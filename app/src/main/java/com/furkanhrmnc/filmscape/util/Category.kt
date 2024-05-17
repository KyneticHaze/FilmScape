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
        /**
         * Category ismine göre o category'i döndürecek fonksiyon.
         */
        fun getCategoryTypeName(name: String?): Category {
            return entries.firstOrNull { it.name == name } ?: POPULAR
        }
    }
}