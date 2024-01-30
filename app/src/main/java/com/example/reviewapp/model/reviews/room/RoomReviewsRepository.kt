package com.example.reviewapp.model.reviews.room

import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.reviews.ReviewsRepository
import com.example.reviewapp.model.reviews.entities.Review
import com.example.reviewapp.model.reviews.room.entities.RatingUpdateTuple
import com.example.reviewapp.model.reviews.room.entities.ReviewDbEntity
import com.example.reviewapp.model.reviews.room.entities.ReviewUpdateTuple
import com.example.reviewapp.model.room.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomReviewsRepository @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ReviewsRepository {

    override suspend fun getReviewByAccountIdAndFilmId(accountId: Long, filmId: Long): Review? {
        return reviewsDao.getReviewByFilmIdAndAccountId(accountId, filmId)?.toReview()
    }


    override suspend fun getReviewsByFilmId(filmId: Long): Flow<List<Review>> =
        withContext(ioDispatcher) {
            return@withContext reviewsDao.getReviewsByFilmId(filmId)
                .map { reviewDbEntity ->
                    reviewDbEntity.map { it.toReview() }
                }
        }

    override suspend fun selectRatingFilm(accountId: Long, filmId: Long, rating: Int) =
        withContext(ioDispatcher) {
            if (getReviewByAccountIdAndFilmId(accountId, filmId) == null) {
                addRating(accountId, filmId, rating)
            } else updateRating(accountId, filmId, rating)
        }

    override suspend fun addReviewForFilm(accountId: Long, filmId: Long, review: String) =
        wrapSQLiteException(ioDispatcher) {
            if (review.isEmpty()) throw EmptyFieldException(Field.Review)

            if (getReviewByAccountIdAndFilmId(accountId, filmId) == null) {
                addReview(accountId, filmId, review)
            } else updateReview(accountId, filmId, review)
            return@wrapSQLiteException
        }

    override suspend fun calculateAverage(filmId: Long): Double = withContext(ioDispatcher) {
        return@withContext reviewsDao.calculateAvg(filmId)
    }

    private fun addReview(accountId: Long, filmId: Long, review: String) {
        val entity = ReviewDbEntity.fromAddReview(accountId, filmId, review)
        reviewsDao.addReview(entity)
    }

    private fun updateReview(accountId: Long, filmId: Long, review: String) {
        reviewsDao.upgradeReview(
            ReviewUpdateTuple(
                accountId = accountId,
                filmId = filmId,
                review = review
            )
        )
    }

    private fun addRating(accountId: Long, filmId: Long, rating: Int) {
        val entity = ReviewDbEntity.fromSelectRating(accountId, filmId, rating)
        reviewsDao.addRating(entity)
    }

    private fun updateRating(accountId: Long, filmId: Long, rating: Int) {
        reviewsDao.upgradeRating(
            RatingUpdateTuple(
                accountId = accountId,
                filmId = filmId,
                rating = rating
            )
        )
    }

}