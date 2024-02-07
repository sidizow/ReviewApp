package com.example.reviewapp.filmfeature.domain.usecases

import com.example.reviewapp.filmfeature.domain.entities.Review
import com.example.reviewapp.filmfeature.domain.repositories.ReviewsRepository
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