package com.example.movieland.domain.model

data class Movie(
    val id : Int?,
    val isAdult : Boolean?,
    val title : String?,
    val poster : String?,
    val description : String?,
    val popularity : Double?,
    val releaseDate : String?,
    val lang : String?,
    val voteAverage : Double?,
    val voteCount : Int?
)
