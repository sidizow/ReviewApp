package com.example.reviewapp.model.reviews.room.entities

import androidx.room.ColumnInfo

data class ReviewUpdateTuple(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "film_id") val filmId: Long,
    val review: String
)

data class RatingUpdateTuple(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "film_id") val filmId: Long,
    val rating: Int
)