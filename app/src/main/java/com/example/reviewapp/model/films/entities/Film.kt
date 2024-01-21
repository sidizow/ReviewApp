package com.example.reviewapp.model.films.entities


//TODO изменить на val
data class Film(
    val id: Long,
    val title: String,
    var description: String,
    val summaryScore: Double,
    val img: String
)
