package com.example.reviewapp.model.reviews.entities

data class Review(
    val idFilm: Long,
    val idAccount: Long,
    var rating: Int?,
    var review: String?
)
