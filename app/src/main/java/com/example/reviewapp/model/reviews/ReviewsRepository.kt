package com.example.reviewapp.model.reviews

import com.example.reviewapp.model.reviews.entities.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    suspend fun getReviewByAccountIdAndFilmId(accountId: Long, filmId: Long): Review?

    suspend fun getReviewsByFilmId(filmId: Long): Flow<List<Review>>

    suspend fun selectRatingFilm(accountId: Long, filmId: Long, rating: Int)

    suspend fun addReviewForFilm(accountId: Long, filmId: Long, review: String)

    suspend fun calculateAverage(filmId: Long): Double

}