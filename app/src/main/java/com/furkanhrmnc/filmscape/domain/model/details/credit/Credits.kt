package com.furkanhrmnc.filmscape.domain.model.details.credit

data class Credits(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)

data class Cast(
    val id: Int,
    val profilePath: String,
    val character: String,
    val name: String
)

data class Crew(
    val id: Int,
    val profilePath: String,
    val job: String,
    val name: String
)
