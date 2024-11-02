package com.furkanhrmnc.filmscape.domain.model

data class MediaDetail(
    val adult: Boolean,
    val backdropPath: String?,
    val createdBy: List<CreatedByModel>?, // TV dizileri için
    val episodeRunTime: List<Int>?, // TV dizileri için
    val firstAirDate: String?, // TV dizileri için
    val genres: List<GenreModel>?, // Ortak alan
    val homepage: String?,
    val id: Int, // Ortak alan
    val inProduction: Boolean?, // TV dizileri için
    val languages: List<String>?, // TV dizileri için
    val lastAirDate: String?, // TV dizileri için
    val lastEpisodeToAir: EpisodeModel?, // TV dizileri için
    val name: String?, // TV dizileri için
    val networks: List<NetworkModel>?, // TV dizileri için
    val nextEpisodeToAir: EpisodeModel?, // TV dizileri için
    val numberOfEpisodes: Int?, // TV dizileri için
    val numberOfSeasons: Int?, // TV dizileri için
    val originCountry: List<String>?, // Ortak alan
    val originalLanguage: String?,
    val originalName: String?, // TV dizileri için
    val originalTitle: String?, // Filmler için
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyModel>?, // Ortak alan
    val productionCountries: List<ProductionCountryModel>?, // Ortak alan
    val releaseDate: String?, // Filmler için
    val revenue: Int?, // Filmler için
    val runtime: Int?, // Filmler için
    val seasons: List<SeasonModel>?, // TV dizileri için
    val spokenLanguages: List<SpokenLanguageModel>?, // Ortak alan
    val status: String?,
    val tagline: String?,
    val title: String?, // Filmler için
    val type: String?, // TV dizileri için
    val video: Boolean?, // Filmler için
    val voteAverage: Double?,
    val voteCount: Int?,
)

data class CreatedByModel(
    val id: Int,
    val creditId: String?,
    val name: String?,
    val gender: Int?,
    val profilePath: String?,
)

data class NetworkModel(
    val id: Int,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?,
)

data class SeasonModel(
    val airDate: String?,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Double?,
)

data class EpisodeModel(
    val airDate: String?,
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String?,
    val runtime: Int?,
    val seasonNumber: Int,
    val voteAverage: Double?,
    val voteCount: Int?,
)

data class GenreModel(
    val id: Int,
    val name: String?,
)

data class ProductionCompanyModel(
    val id: Int,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?,
)

data class ProductionCountryModel(
    val iso31661: String?,
    val name: String?,
)

data class SpokenLanguageModel(
    val englishName: String?,
    val iso6391: String?,
    val name: String?,
)
