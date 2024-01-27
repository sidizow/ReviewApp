package com.example.reviewapp.model.reviews

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reviewapp.model.reviews.room.entities.RatingUpdateTuple
import com.example.reviewapp.model.reviews.room.entities.ReviewDbEntity
import com.example.reviewapp.model.reviews.room.entities.ReviewUpdateTuple


//TODO поменять на флоу получение листа
@Dao
interface ReviewsDao {

    @Query("SELECT * FROM accounts_films_reviews WHERE account_id = :accountId AND film_id =:filmId ")
    fun getReviewByFilmIdAndAccountId(accountId: Long, filmId: Long): ReviewDbEntity?

    @Query("SELECT * FROM accounts_films_reviews WHERE film_id = :filmId")
    fun getReviewsByFilmId(filmId: Long): List<ReviewDbEntity>

    @Insert
    fun addReview(reviewDbEntity: ReviewDbEntity)

    @Update(entity = ReviewDbEntity::class)
    fun upgradeReview(review: ReviewUpdateTuple)

    @Insert
    fun addRating(reviewDbEntity: ReviewDbEntity)

    @Update(entity = ReviewDbEntity::class)
    fun upgradeRating(review: RatingUpdateTuple)

    @Query("SELECT AVG(rating) FROM accounts_films_reviews WHERE film_id = :filmId")
    fun calculateAvg(filmId: Long): Double
}