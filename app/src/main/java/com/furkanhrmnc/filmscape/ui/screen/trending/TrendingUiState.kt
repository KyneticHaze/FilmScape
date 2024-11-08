package com.furkanhrmnc.filmscape.ui.screen.trending

import com.furkanhrmnc.filmscape.util.Time

data class TrendUiState(
    val period: Time = Time.DAY,
    val option: String = "Day",
    val expanded: Boolean = false,
    val error: Throwable? = null,
)