package com.example.reviewapp.catalogfilmsfeature.domain.entities


data class Film(
    val id: Long,
    val title: String,
    val description: String,
    val summaryScore: Double,
    val img: String
)
