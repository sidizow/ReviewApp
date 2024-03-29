package com.example.film.domain.usecases

import com.example.film.domain.repositories.ReviewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculateAverageUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
) {

    suspend fun calculateAverage(filmId: Long): Double =
        reviewsRepository.calculateAverage(filmId = filmId)

}