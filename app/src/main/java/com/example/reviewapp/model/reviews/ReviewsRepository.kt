package com.example.reviewapp.model.reviews

import com.example.reviewapp.model.reviews.entities.Review

//TODO обработать все null
interface ReviewsRepository {

    suspend fun getReviewByAccountIdAndFilmId(accountId: Long, filmId: Long): Review?

    suspend fun getRatingByAccountIdAndFilmId(accountId: Long, filmId: Long): Int?

    suspend fun getReviewsByFilmId(filmId: Long): List<Review>

    suspend fun selectRatingFilm(accountId: Long, filmId: Long, rating: Int)

    suspend fun addReviewForFilm(accountId: Long, filmId: Long, review: String)

    suspend fun calculateAverage(filmId: Long): Double

}