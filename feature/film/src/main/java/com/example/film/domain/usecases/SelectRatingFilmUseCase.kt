package com.example.film.domain.usecases

import com.example.film.domain.repositories.ReviewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectRatingFilmUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
) {

    suspend fun selectRatingFilm(accountId: Long, filmId: Long, rating: Int) {
        reviewsRepository.selectRatingFilm(accountId = accountId, filmId = filmId, rating = rating)
    }
}