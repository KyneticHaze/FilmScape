package com.furkanhrmnc.filmscape.data.network.dto.detail

import com.furkanhrmnc.filmscape.domain.model.CreatedByModel
import com.furkanhrmnc.filmscape.domain.model.EpisodeModel
import com.furkanhrmnc.filmscape.domain.model.GenreModel
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.NetworkModel
import com.furkanhrmnc.filmscape.domain.model.ProductionCompanyModel
import com.furkanhrmnc.filmscape.domain.model.ProductionCountryModel
import com.furkanhrmnc.filmscape.domain.model.SeasonModel
import com.furkanhrmnc.filmscape.domain.model.SpokenLanguageModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailDTO(
    @SerialName("adult")
    val adult: Boolean?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("created_by")
    val createdBy: List<CreatedBy?>?, // Sadece TV dizileri için geçerli

    @SerialName("episode_run_time")
    val episodeRunTime: List<Int?>?, // Sadece TV dizileri için geçerli

    @SerialName("first_air_date")
    val firstAirDate: String?, // Sadece TV dizileri için geçerli

    @SerialName("genres")
    val genres: List<Genre?>?, // Ortak alan (film ve dizi türleri)

    @SerialName("homepage")
    val homepage: String?,

    @SerialName("id")
    val id: Int?, // Ortak alan

    @SerialName("in_production")
    val inProduction: Boolean?, // Sadece TV dizileri için geçerli

    @SerialName("languages")
    val languages: List<String?>?, // Sadece TV dizileri için geçerli

    @SerialName("last_air_date")
    val lastAirDate: String?, // Sadece TV dizileri için geçerli

    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: Episode?, // Sadece TV dizileri için geçerli

    @SerialName("name")
    val name: String?, // TV dizileri için ad

    @SerialName("networks")
    val networks: List<Network?>?, // Sadece TV dizileri için geçerli

    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: Episode?, // Sadece TV dizileri için geçerli

    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int?, // Sadece TV dizileri için geçerli

    @SerialName("number_of_seasons")
    val numberOfSeasons: Int?, // Sadece TV dizileri için geçerli

    @SerialName("origin_country")
    val originCountry: List<String?>?, // Ortak alan (Film ve dizi için)

    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("original_name")
    val originalName: String?, // Sadece TV dizileri için geçerli

    @SerialName("original_title")
    val originalTitle: String?, // Sadece filmler için geçerli

    @SerialName("overview")
    val overview: String?,

    @SerialName("popularity")
    val popularity: Double?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>?, // Ortak alan

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>?, // Ortak alan

    @SerialName("release_date")
    val releaseDate: String?, // Sadece filmler için geçerli

    @SerialName("revenue")
    val revenue: Int?, // Sadece filmler için geçerli

    @SerialName("runtime")
    val runtime: Int?, // Sadece filmler için geçerli

    @SerialName("seasons")
    val seasons: List<Season?>?, // Sadece TV dizileri için geçerli

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?, // Ortak alan

    @SerialName("status")
    val status: String?,

    @SerialName("tagline")
    val tagline: String?,

    @SerialName("title")
    val title: String?, // Sadece filmler için geçerli

    @SerialName("type")
    val type: String?, // Sadece TV dizileri için geçerli

    @SerialName("video")
    val video: Boolean?, // Sadece filmler için geçerli

    @SerialName("vote_average")
    val voteAverage: Double?,

    @SerialName("vote_count")
    val voteCount: Int?,
)


// MediaDetailDTO'dan MediaDetail Model'e dönüştürme fonksiyonu
fun MediaDetailDTO.mapToModel(): MediaDetail {
    return MediaDetail(
        adult = adult ?: false,
        backdropPath = backdropPath,
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
        posterPath = posterPath,
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
    creditId = creditİd,
    name = name,
    gender = gender,
    profilePath = profilePath
)

fun Network.mapToModel() = NetworkModel(
    id = id ?: 0,
    name = name,
    logoPath = logoPath,
    originCountry = originCountry
)

fun Season.mapToModel() = SeasonModel(
    airDate = airDate,
    episodeCount = episodeCount ?: 0,
    id = id ?: 0,
    name = name.orEmpty(),
    overview = overview,
    posterPath = posterPath as? String,
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
    logoPath = logoPath,
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

