package com.furkanhrmnc.filmscape.data.network.dto


import com.furkanhrmnc.filmscape.data.network.dto.credit.CastDTO
import com.furkanhrmnc.filmscape.data.network.dto.credit.CrewDTO
import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.Serializable

@Serializable
data class CreditResponse(
    val cast: List<CastDTO?>? = null,
    val crew: List<CrewDTO?>? = null,
    val id: Int?,
)

fun CreditResponse.toCastList(): List<Cast> {
    return cast?.filterNotNull()?.map { cast ->
        Cast(
            adult = cast.adult ?: false,
            castId = cast.castId ?: 0,
            character = cast.character ?: "",
            creditId = cast.creditId ?: "",
            gender = cast.gender ?: 0,
            id = cast.id ?: 0,
            knownForDepartment = cast.knownForDepartment ?: "",
            name = cast.name ?: "",
            order = cast.order ?: 0,
            originalName = cast.originalName ?: "",
            popularity = cast.popularity ?: 0.0,
            profilePath = imageFormatter(cast.profilePath),
        )
    } ?: emptyList()
}