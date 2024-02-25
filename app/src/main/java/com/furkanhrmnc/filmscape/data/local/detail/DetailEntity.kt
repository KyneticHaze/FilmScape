package com.furkanhrmnc.filmscape.data.local.detail

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.furkanhrmnc.filmscape.common.Constants.CREATED_BY_TABLE
import com.furkanhrmnc.filmscape.common.Constants.DETAIL_TABLE
import com.furkanhrmnc.filmscape.common.Constants.SEASON_TABLE
import com.furkanhrmnc.filmscape.domain.model.Created
import com.furkanhrmnc.filmscape.domain.model.Episode
import com.furkanhrmnc.filmscape.domain.model.SeasonModel

@Entity(tableName = DETAIL_TABLE)
data class DetailEntity(
    @PrimaryKey val id: Int,

    val backdropPath: String,
    val posterPath: String,
    val isAdult: Boolean,
    val description: String,
    val originalName: String,
    val originalTitle: String,
    val voteAverage: Double,
    val voteCount: Int,
    val mediaUrl: String,
    val status: String,
    val tagline: String,
    val movieDate: String,
    val tvDate: String,
    val numberEpisode: Int,
    val numberSeason: Int,
    @Embedded val lastEpisode: Episode,
    @Embedded val nextEpisode: Episode,
    @Relation(
        parentColumn = "id",
        entityColumn = "createdId",
        entity = CreatedEntity::class
    )
    val createdBy: List<Created>,
    @Relation(
        parentColumn = "id",
        entityColumn = "seasonId",
        entity = SeasonModelEntity::class
    )
    val seasons: List<SeasonModel>
)

@Entity(tableName = CREATED_BY_TABLE)
data class CreatedEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val createdUrl: String,
    val createdId: Int // DetailEntity ile ilişki için foreign key
)

@Entity(tableName = SEASON_TABLE)
data class SeasonModelEntity(
    @PrimaryKey val id: Int,
    val seasonNumber: Int,
    val name: String,
    val image: String,
    val voteAverage: Double,
    val seasonId: Int // DetailEntity ile ilişki için foreign key
)