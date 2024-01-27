package com.example.reviewapp.model.reviews.entities

data class Review(
    val filmId: Long,
    val accountId: Long,
    var rating: Int?,
    var review: String?
)
