package com.example.film.domain.usecases

import com.example.film.domain.repositories.ReviewsRepository
import com.example.presentation.constants.Field
import com.example.presentation.exceptions.EmptyFieldException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddReviewForFilmUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
) {

    suspend fun addReviewForFilm(accountId: Long, filmId: Long, review: String){
        if (review.isEmpty()) throw EmptyFieldException(Field.Review)
        reviewsRepository.addReviewForFilm(accountId = accountId, filmId = filmId, review = review)
    }


}