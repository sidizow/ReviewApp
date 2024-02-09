package com.example.film.domain.entities

data class Film(
    val id: Long,
    val title: String,
    val description: String,
    val summaryScore: Double,
    val img: String
)