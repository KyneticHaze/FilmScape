package com.furkanhrmnc.filmscape.data.remote.dto.details.credit

import com.furkanhrmnc.filmscape.domain.model.details.credit.Credits

data class CreditResponse(
    val id: Int?,
    val cast: List<CastDTO>?,
    val crew: List<CrewDTO>?,
)

fun CreditResponse.toCredit(): Credits = Credits(
    id = id ?: 1,
    cast = cast?.map(CastDTO::toCast) ?: emptyList(),
    crew = crew?.map(CrewDTO::toCrew) ?: emptyList()
)