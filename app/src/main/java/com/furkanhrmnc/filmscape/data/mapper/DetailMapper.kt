package com.furkanhrmnc.filmscape.data.mapper

import com.furkanhrmnc.filmscape.common.Constants
import com.furkanhrmnc.filmscape.data.remote.dto.detail.CreatedBy
import com.furkanhrmnc.filmscape.data.remote.dto.detail.DetailDTO
import com.furkanhrmnc.filmscape.data.remote.dto.detail.EpisodeToAir
import com.furkanhrmnc.filmscape.data.remote.dto.detail.Season
import com.furkanhrmnc.filmscape.domain.model.Created
import com.furkanhrmnc.filmscape.domain.model.Detail
import com.furkanhrmnc.filmscape.domain.model.Episode
import com.furkanhrmnc.filmscape.domain.model.SeasonModel

fun DetailDTO.toDetail(): Detail = Detail(
    id = id ?: 1,
    backdropPath = backdropPath ?: Constants.unavailable,
    posterPath = posterPath ?: Constants.unavailable,
    isAdult = adult ?: false,
    mediaUrl = homepage ?: Constants.unavailable,
    originalTitle = originalTitle ?: Constants.unavailable,
    originalName = originalName ?: Constants.unavailable,
    description = overview ?: Constants.unavailable,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    status = status ?: Constants.unavailable,
    tagline = tagline ?: Constants.unavailable,
    movieDate = releaseDate ?: Constants.unavailable,
    tvDate = firstAirDate ?: Constants.unavailable,
    numberEpisode = numberOfEpisodes ?: 1,
    numberSeason = numberOfSeasons ?: 1,
    lastEpisode = lastEpisodeToAir!!.toEpisode(),
    nextEpisode = nextEpisodeToAir!!.toEpisode(),
    createdBy = createdBy!!.map { it.toCreated() },
    seasons = seasons!!.map { it.toSeasonModel() }
)

fun EpisodeToAir.toEpisode(): Episode = Episode(
    episodeName = name ?: Constants.unavailable,
    episodeNumber = episodeNumber ?: 1,
    date = airDate ?: Constants.unavailable,
    voteCount = voteCount ?: 0,
    voteAverage = voteAverage ?: 0.0,
)

fun CreatedBy.toCreated(): Created = Created(
    name = name ?: Constants.unavailable,
    createdUrl = profilePath ?: Constants.unavailable
)

fun Season.toSeasonModel(): SeasonModel = SeasonModel(
    date = airDate ?: Constants.unavailable,
    episodeNumber = episodeCount ?: 1,
    seasonNumber = seasonNumber ?: 1,
    name = name ?: Constants.unavailable,
    image = posterPath ?: Constants.unavailable,
    voteAverage = voteAverage ?: 0.0,
)