package com.example.reviewapp.model.reviews

import com.example.reviewapp.model.reviews.entities.Review

interface ReviewsRepository {

    suspend fun getReview(): List<Review>

    suspend fun getIndexReviewByIdFilmAndIdAccount(idFilm: Long, idAccount: Long): Int
    suspend fun getReviewByIdFilm(idFilm: Long): List<Review>

    suspend fun selectRatingFilm(idAccount: Long, idFilm: Long, rating: Int)

    suspend fun addReviewForFilm(idAccount: Long, idFilm: Long, review: String)

}