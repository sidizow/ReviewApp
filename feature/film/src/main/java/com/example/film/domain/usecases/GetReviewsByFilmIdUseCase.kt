package com.example.film.domain.usecases

import com.example.film.domain.entities.Review
import com.example.film.domain.repositories.ReviewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviewsByFilmIdUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository
){

    suspend fun getReviewsByFilmId(filmId: Long): Flow<List<Review>> =
        reviewsRepository.getReviewsByFilmId(filmId = filmId)

}