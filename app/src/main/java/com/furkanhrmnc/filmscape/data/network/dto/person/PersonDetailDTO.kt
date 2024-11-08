package com.furkanhrmnc.filmscape.data.network.dto.person


import com.furkanhrmnc.filmscape.domain.model.PersonDetail
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailDTO(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String?>?,
    @SerialName("biography")
    val biography: String?,
    @SerialName("birthday")
    val birthday: String?,
    @SerialName("deathday")
    val deathDay: String? = null,
    @SerialName("gender")
    val gender: Int?,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("known_for_department")
    val knownForDepartment: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("place_of_birth")
    val placeOfBirth: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("profile_path")
    val profilePath: String?,
)

fun PersonDetailDTO.toPersonDetail(): PersonDetail = PersonDetail(
    adult = adult ?: false,
    alsoKnownAs = alsoKnownAs?.filterNotNull() ?: emptyList(),
    biography = biography ?: "",
    birthDay = birthday ?: "",
    deathDay = deathDay ?: "",
    gender = gender ?: 0,
    homepage = homepage ?: "",
    id = id ?: 0,
    imdbId = imdbId ?: "",
    knownForDepartment = knownForDepartment ?: "",
    name = name ?: "",
    placeOfBirth = placeOfBirth ?: "",
    popularity = popularity ?: 0.0,
    profilePath = imageFormatter(profilePath),
)