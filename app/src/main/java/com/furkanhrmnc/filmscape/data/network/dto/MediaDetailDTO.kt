package com.furkanhrmnc.filmscape.data.network.dto

import com.furkanhrmnc.filmscape.data.network.dto.detail.CreatedBy
import com.furkanhrmnc.filmscape.data.network.dto.detail.Episode
import com.furkanhrmnc.filmscape.data.network.dto.detail.Genre
import com.furkanhrmnc.filmscape.data.network.dto.detail.Network
import com.furkanhrmnc.filmscape.data.network.dto.detail.ProductionCompany
import com.furkanhrmnc.filmscape.data.network.dto.detail.ProductionCountry
import com.furkanhrmnc.filmscape.data.network.dto.detail.Season
import com.furkanhrmnc.filmscape.data.network.dto.detail.SpokenLanguage
import com.furkanhrmnc.filmscape.domain.model.CreatedByModel
import com.furkanhrmnc.filmscape.domain.model.EpisodeModel
import com.furkanhrmnc.filmscape.domain.model.GenreModel
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.NetworkModel
import com.furkanhrmnc.filmscape.domain.model.ProductionCompanyModel
import com.furkanhrmnc.filmscape.domain.model.ProductionCountryModel
import com.furkanhrmnc.filmscape.domain.model.SeasonModel
import com.furkanhrmnc.filmscape.domain.model.SpokenLanguageModel
import com.furkanhrmnc.filmscape.util.Constants.imageFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailDTO(
    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("created_by")
    val createdBy: List<CreatedBy?>? = null, // Sadece TV dizileri için geçerli

    @SerialName("episode_run_time")
    val episodeRunTime: List<Int?>? = null, // Sadece TV dizileri için geçerli

    @SerialName("first_air_date")
    val firstAirDate: String? = null, // Sadece TV dizileri için geçerli

    @SerialName("genres")
    val genres: List<Genre?>? = null, // Ortak alan (film ve dizi türleri)

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("id")
    val id: Int? = null, // Ortak alan

    @SerialName("in_production")
    val inProduction: Boolean? = null, // Sadece TV dizileri için geçerli

    @SerialName("languages")
    val languages: List<String?>? = null, // Sadece TV dizileri için geçerli

    @SerialName("last_air_date")
    val lastAirDate: String? = null, // Sadece TV dizileri için geçerli

    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: Episode? = null, // Sadece TV dizileri için geçerli

    @SerialName("name")
    val name: String? = null, // TV dizileri için ad

    @SerialName("networks")
    val networks: List<Network?>? = null, // Sadece TV dizileri için geçerli

    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: Episode? = null, // Sadece TV dizileri için geçerli

    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null, // Sadece TV dizileri için geçerli

    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null, // Sadece TV dizileri için geçerli

    @SerialName("origin_country")
    val originCountry: List<String?>? = null, // Ortak alan (Film ve dizi için)

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_name")
    val originalName: String? = null, // Sadece TV dizileri için geçerli

    @SerialName("original_title")
    val originalTitle: String? = null, // Sadece filmler için geçerli

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null, // Ortak alan

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null, // Ortak alan

    @SerialName("release_date")
    val releaseDate: String? = null, // Sadece filmler için geçerli

    @SerialName("revenue")
    val revenue: Int? = null, // Sadece filmler için geçerli

    @SerialName("runtime")
    val runtime: Int? = null, // Sadece filmler için geçerli

    @SerialName("seasons")
    val seasons: List<Season?>? = null, // Sadece TV dizileri için geçerli

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null, // Ortak alan

    @SerialName("status")
    val status: String? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("title")
    val title: String? = null, // Sadece filmler için geçerli

    @SerialName("type")
    val type: String? = null, // Sadece TV dizileri için geçerli

    @SerialName("video")
    val video: Boolean? = null, // Sadece filmler için geçerli

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,
)


// MediaDetailDTO'dan MediaDetail Model'e dönüştürme fonksiyonu
fun MediaDetailDTO.mapToModel(): MediaDetail {
    return MediaDetail(
        adult = adult ?: false,
        backdropPath = imageFormatter(backdropPath),
        posterPath = imageFormatter(posterPath),
        createdBy = createdBy?.mapNotNull { it?.mapToModel() },
        episodeRunTime = episodeRunTime?.filterNotNull() ?: emptyList(),
        firstAirDate = firstAirDate,
        genres = genres?.mapNotNull { it?.mapToModel() },
        homepage = homepage,
        id = id ?: 0,
        inProduction = inProduction,
        languages = languages?.filterNotNull() ?: emptyList(),
        lastAirDate = lastAirDate,
        lastEpisodeToAir = lastEpisodeToAir?.mapToModel(),
        name = name,
        networks = networks?.mapNotNull { it?.mapToModel() },
        nextEpisodeToAir = nextEpisodeToAir?.mapToModel(),
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originCountry = originCountry?.filterNotNull() ?: emptyList(),
        originalLanguage = originalLanguage,
        originalName = originalName,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        productionCompanies = productionCompanies?.mapNotNull { it?.mapToModel() },
        productionCountries = productionCountries?.mapNotNull { it?.mapToModel() },
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        seasons = seasons?.mapNotNull { it?.mapToModel() },
        spokenLanguages = spokenLanguages?.mapNotNull { it?.mapToModel() },
        status = status,
        tagline = tagline,
        title = title,
        type = type,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

// Alt DTO'lardan model sınıflarına dönüştürme fonksiyonları

fun CreatedBy.mapToModel() = CreatedByModel(
    id = id ?: 0,
    creditId = creditId,
    name = name,
    gender = gender,
    profilePath = imageFormatter(profilePath)
)

fun Network.mapToModel() = NetworkModel(
    id = id ?: 0,
    name = name,
    logoPath = imageFormatter(logoPath),
    originCountry = originCountry
)

fun Season.mapToModel() = SeasonModel(
    airDate = airDate,
    episodeCount = episodeCount ?: 0,
    id = id ?: 0,
    name = name.orEmpty(),
    overview = overview,
    posterPath = imageFormatter(posterPath),
    seasonNumber = seasonNumber ?: 0,
    voteAverage = voteAverage
)

fun Episode.mapToModel() = EpisodeModel(
    airDate = airDate,
    episodeNumber = episodeNumber ?: 0,
    id = id ?: 0,
    name = name.orEmpty(),
    overview = overview,
    runtime = runtime,
    seasonNumber = seasonNumber ?: 0,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun Genre.mapToModel() = GenreModel(
    id = id ?: 0,
    name = name
)

fun ProductionCompany.mapToModel() = ProductionCompanyModel(
    id = id ?: 0,
    name = name,
    logoPath = imageFormatter(logoPath),
    originCountry = originCountry
)

fun ProductionCountry.mapToModel() = ProductionCountryModel(
    iso31661 = iso31661,
    name = name
)

fun SpokenLanguage.mapToModel() = SpokenLanguageModel(
    englishName = englishName,
    iso6391 = iso6391,
    name = name
)

