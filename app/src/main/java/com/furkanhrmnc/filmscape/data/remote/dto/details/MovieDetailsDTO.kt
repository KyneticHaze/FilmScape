package com.furkanhrmnc.filmscape.data.remote.dto.details


import com.furkanhrmnc.filmscape.data.remote.dto.genre.GenreDTO
import com.furkanhrmnc.filmscape.data.remote.dto.genre.toGenre
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.util.ApiConfig
import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    val budget: Int?,
    val genres: List<GenreDTO>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDTO>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)

fun MovieDetailsDTO.toMovieDetails(): MovieDetails = MovieDetails(
    id = id ?: 1,
    backdropPath = "${ApiConfig.IMAGE_URL}$backdropPath" ?: "",
    posterPath = "${ApiConfig.IMAGE_URL}$posterPath" ?: "",
    isAdult = adult ?: false,
    budget = budget ?: 0,
    originalCountry = originCountry ?: emptyList(),
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    description = overview ?: "",
    popularity = popularity ?: 0.0,
    link = homepage ?: "",
    imdbId = imdbId ?: "",
    tagline = tagline ?: "",
    status = status ?: "",
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    releaseDate = releaseDate ?: "",
    voteAverage = voteAverage?.toFloat() ?: 0f,
    voteCount = voteCount ?: 0,
    genres = genres?.map(GenreDTO::toGenre) ?: emptyList(),
    spokenLanguages = spokenLanguages?.map(SpokenLanguageDTO::toLanguage) ?: emptyList(),
    productionCountries = productionCountries?.map(ProductionCountryDTO::toCountry) ?: emptyList(),
    productionCompanies = productionCompanies?.map(ProductionCompanyDTO::toCompany) ?: emptyList(),
)