package com.example.reviewapp.filmfeature.domain.usecases

import com.example.reviewapp.filmfeature.domain.repositories.ReviewsRepository
import com.example.reviewapp.core.exceptions.EmptyFieldException
import com.example.reviewapp.core.constants.Field
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