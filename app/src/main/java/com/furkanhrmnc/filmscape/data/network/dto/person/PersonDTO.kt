package com.furkanhrmnc.filmscape.data.network.dto.person


import com.furkanhrmnc.filmscape.domain.model.Person
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDTO(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int?,
    @SerialName("known_for")
    val knownFor: List<KnownForDTO?>?,
    @SerialName("known_for_department")
    val knownForDepartment: String?,
    val name: String?,
    @SerialName("original_name")
    val originalName: String?,
    val popularity: Double?,
    @SerialName("profile_path")
    val profilePath: String?,
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
