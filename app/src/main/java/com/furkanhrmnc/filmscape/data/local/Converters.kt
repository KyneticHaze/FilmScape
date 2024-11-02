package com.furkanhrmnc.filmscape.data.local


import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>?): String {
        return Json.encodeToString(genreIds ?: emptyList())
    }

    @TypeConverter
    fun toGenreIds(genreIdsString: String): List<Int> {
        return Json.decodeFromString(genreIdsString)
    }

    @TypeConverter
    fun fromOriginCountry(originCountry: List<String>?): String {
        return Json.encodeToString(originCountry ?: emptyList())
    }

    @TypeConverter
    fun toOriginCountry(originCountryString: String): List<String> {
        return Json.decodeFromString(originCountryString)
    }
}
