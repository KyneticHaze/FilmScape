package com.furkanhrmnc.filmscape.data.network.dto.person


import com.furkanhrmnc.filmscape.domain.model.Person
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDTO(
    val adult: Boolean? = null,
    val gender: Int? = null,
    val id: Int? = null,
    @SerialName("known_for")
    val knownFor: List<KnownForDTO?>? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    val popularity: Double? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
)

fun PersonDTO.toPerson(): Person = Person(
    adult = adult ?: false,
    gender = gender ?: 0,
    id = id ?: 0,
    knownFor = knownFor?.mapNotNull { it?.toKnownFor() } ?: emptyList(),
    knownForDepartment = knownForDepartment ?: "",
    name = name ?: "",
    originalName = originalName ?: "",
    popularity = popularity ?: 0.0,
    profilePath = imageFormatter(profilePath),
)
