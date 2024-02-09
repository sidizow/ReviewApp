package com.example.reviewapp.glue.film

import com.example.film.domain.entities.Review
import com.example.film.domain.repositories.ReviewsRepository
import com.example.reviewapp.data.datasources.room.database.wrapSQLiteException
import com.example.reviewapp.data.datasources.room.reviews.ReviewsDao
import com.example.reviewapp.data.datasources.room.reviews.entities.RatingUpdateTuple
import com.example.reviewapp.data.datasources.room.reviews.entities.ReviewDbEntity
import com.example.reviewapp.data.datasources.room.reviews.entities.ReviewUpdateTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterReviewsRepository @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ReviewsRepository {

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
            if (getReviewByAccountIdAndFilmId(accountId, filmId) == null) {
                addReview(accountId, filmId, review)
            } else updateReview(accountId, filmId, review)
            return@wrapSQLiteException
        }


    override suspend fun calculateAverage(filmId: Long): Double = withContext(ioDispatcher) {
        return@withContext reviewsDao.calculateAvg(filmId)
    }

    private fun getReviewByAccountIdAndFilmId(accountId: Long, filmId: Long): Review? =
        reviewsDao.getReviewByFilmIdAndAccountId(accountId, filmId)?.toReview()

    private fun addReview(accountId: Long, filmId: Long, review: String) {
        val entity = fromAddReview(accountId, filmId, review)
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
        val entity = fromSelectRating(accountId, filmId, rating)
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

    private fun ReviewDbEntity.toReview(): Review = Review(
        filmId = filmId,
        accountId = accountId,
        rating = rating,
        review = review
    )

    companion object{
        fun fromAddReview(accountId: Long, filmId: Long, review: String): ReviewDbEntity = ReviewDbEntity(
            accountId = accountId,
            filmId = filmId,
            rating = null,
            review = review
        )

        fun fromSelectRating(accountId: Long, filmId: Long, rating: Int): ReviewDbEntity = ReviewDbEntity(
            accountId = accountId,
            filmId = filmId,
            rating = rating,
            review = null
        )
    }

}