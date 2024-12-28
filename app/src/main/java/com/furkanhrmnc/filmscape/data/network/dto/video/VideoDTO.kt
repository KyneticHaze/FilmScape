package com.furkanhrmnc.filmscape.data.network.dto.video


import com.furkanhrmnc.filmscape.domain.model.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDTO(
    @SerialName("id")
    val id: String? = null,
    @SerialName("iso_3166_1")
    val iso31661: String? = null,
    @SerialName("iso_639_1")
    val iso6391: String? = null,
    @SerialName("key")
    val key: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("official")
    val official: Boolean? = null,
    @SerialName("published_at")
    val publishedAt: String? = null,
    @SerialName("site")
    val site: String? = null,
    @SerialName("size")
    val size: Int? = null,
    @SerialName("type")
    val type: String? = null,
)


fun VideoDTO.toVideo() = Video(
    id = id ?: "",
    name = name ?: "",
    iso31661 = iso31661 ?: "",
    iso6391 = iso6391 ?: "",
    publishedAt = publishedAt ?: "",
    site = site ?: "",
    size = size ?: 0,
    type = type ?: "",
    key = key ?: "",
    official = official ?: false,
)