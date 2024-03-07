package com.furkanhrmnc.filmscape.domain.usecase

class TimeMaker(
    private val minutes: Int
) {
    operator fun invoke(): String {
        val hours = minutes / 60
        val remainingHours = minutes % 60
        return String.format("%02d hr %02d min", hours, remainingHours)
    }
}