package com.example.film.domain.repositories

import com.example.film.domain.entities.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    suspend fun getReviewsByFilmId(filmId: Long): Flow<List<Review>>

    suspend fun selectRatingFilm(accountId: Long, filmId: Long, rating: Int)

    suspend fun addReviewForFilm(accountId: Long, filmId: Long, review: String)

    suspend fun calculateAverage(filmId: Long): Double

}