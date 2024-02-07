package com.example.reviewapp.filmfeature.domain.entities

data class Review(
    val filmId: Long,
    val accountId: Long,
    var rating: Int?,
    var review: String?
)
