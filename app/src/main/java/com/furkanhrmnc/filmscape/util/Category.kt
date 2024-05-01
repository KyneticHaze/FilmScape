package com.furkanhrmnc.filmscape.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Bu sınıf, kategorilere göre film çekme isteğini daha basit işlemek için yazıldı.
 */
@Parcelize
enum class Category: Parcelable {
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
}