package com.furkanhrmnc.filmscape.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding

/**
 * Movie sınıfının releaseDate verisini zaman olarak biçimlendirmek için yazıldı.
 *
 * @author Furkan Harmancı
 */
object Date {
    private val RemoteDateFormat = LocalDate.Format {
        year()
        chars("-")
        monthNumber(padding = Padding.SPACE)
        chars("-")
        dayOfMonth()
    }
    private val LocalDateFormat = LocalDate.Format {
        dayOfMonth()
        chars("/")
        monthName(names = MonthNames.ENGLISH_ABBREVIATED)
        chars("/")
        year()
    }

    private fun parse(
        date: String,
        format: DateTimeFormat<LocalDate> = RemoteDateFormat,
    ): LocalDate =
        LocalDate.parse(input = date, format = format)

    fun format(
        date: String,
        format: DateTimeFormat<LocalDate> = LocalDateFormat,
    ): String =
        parse(date).format(format)
}