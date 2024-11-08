package com.furkanhrmnc.filmscape.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import java.time.DateTimeException

/**
 * Movie sınıfının releaseDate verisini zaman olarak biçimlendirmek için yazıldı.
 *
 * @author Furkan Harmancı
 */
object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun parse(date: String): LocalDate? {
        return try {
            if (date.isBlank()) {
                Log.d("DateFormatter", "Date string is blank.")
                return null
            }
            val parsedDate = LocalDate.parse(date)
            Log.d("DateFormatter", "Parsed date: $parsedDate")
            parsedDate
        } catch (e: DateTimeException) {
            Log.e("DateFormatter", "Failed to parse date: $date", e)
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun format(date: String?): String {
        if (date == null) return "Unknown Date"

        val parsedDate = parse(date)
        return parsedDate?.let {
            "${it.dayOfMonth}/${it.month.name.take(3)}/${it.year}"
        } ?: "N/A"
    }
}