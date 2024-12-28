package com.furkanhrmnc.filmscape.data.network.dto.person


import com.furkanhrmnc.filmscape.domain.model.PersonDetail
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailDTO(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String?>? = null,
    @SerialName("biography")
    val biography: String? = null,
    @SerialName("birthday")
    val birthday: String? = null,
    @SerialName("deathday")
    val deathDay: String? = null,
    @SerialName("gender")
    val gender: Int? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("place_of_birth")
    val placeOfBirth: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
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