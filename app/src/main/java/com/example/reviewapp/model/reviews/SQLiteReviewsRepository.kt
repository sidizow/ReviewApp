package com.example.reviewapp.model.reviews

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.reviews.entities.Review
import com.example.reviewapp.model.sqlite.AppSQLiteContract.AccountsFilmsReviewsTable
import com.example.reviewapp.model.sqlite.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SQLiteReviewsRepository @Inject constructor(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher,
) : ReviewsRepository {

    override suspend fun getReviewByIdFilmAndIdAccount(idFilm: Long, idAccount: Long): Review? {
        val cursor = db.query(
            AccountsFilmsReviewsTable.TABLE_NAME,
            arrayOf(
                AccountsFilmsReviewsTable.COLUMN_FILM_ID,
                AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID,
                AccountsFilmsReviewsTable.COLUMN_RATING,
                AccountsFilmsReviewsTable.COLUMN_REVIEW
            ),
            "${AccountsFilmsReviewsTable.COLUMN_FILM_ID} = ? AND ${AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID} = ?",
            arrayOf(idFilm.toString(), idAccount.toString()),
            null, null, null
        )
        return cursor.use {
            if (cursor.count == 0) return@use null
            cursor.moveToFirst()
            parseReview(cursor)
        }
    }

    override suspend fun getRatingByIdFilmAndIdAccount(idFilm: Long, idAccount: Long): Int? =
        withContext(ioDispatcher) {
            getReviewByIdFilmAndIdAccount(idFilm, idAccount)?.rating
        }


    override suspend fun getReviewByIdFilm(idFilm: Long): List<Review> = withContext(ioDispatcher){
        val cursor = db.query(
            AccountsFilmsReviewsTable.TABLE_NAME,
            arrayOf(
                AccountsFilmsReviewsTable.COLUMN_FILM_ID,
                AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID,
                AccountsFilmsReviewsTable.COLUMN_RATING,
                AccountsFilmsReviewsTable.COLUMN_REVIEW
            ),
            "${AccountsFilmsReviewsTable.COLUMN_FILM_ID} = ?",
            arrayOf(idFilm.toString()),
            null, null, null
        )
        return@withContext cursor.use {
            val list = mutableListOf<Review>()
            while (cursor.moveToNext()) {
                list.add(parseReview(cursor))
            }
            return@use list
        }
    }

    override suspend fun selectRatingFilm(idAccount: Long, idFilm: Long, rating: Int) = withContext(ioDispatcher) {
        if (getReviewByIdFilmAndIdAccount(idFilm, idAccount) == null) {
            addRating(idAccount, idFilm, rating)
        } else updateRating(idAccount, idFilm, rating)
    }

    override suspend fun addReviewForFilm(idAccount: Long, idFilm: Long, review: String) = wrapSQLiteException(ioDispatcher){
        if (review.isEmpty()) throw EmptyFieldException(Field.Review)

        if (getReviewByIdFilmAndIdAccount(idFilm, idAccount) == null) {
            addReview(idAccount, idFilm, review)
        } else updateReview(idAccount, idFilm, review)

        return@wrapSQLiteException
    }

    private fun addReview(idAccount: Long, idFilm: Long, review: String) {
        db.insert(
            AccountsFilmsReviewsTable.TABLE_NAME,
            null,
            contentValuesOf(
                AccountsFilmsReviewsTable.COLUMN_FILM_ID to idFilm,
                AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID to idAccount,
                AccountsFilmsReviewsTable.COLUMN_REVIEW to review
            )
        )
    }

    private fun updateReview(idAccount: Long, idFilm: Long, review: String) {
        db.update(
            AccountsFilmsReviewsTable.TABLE_NAME,
            contentValuesOf(
                AccountsFilmsReviewsTable.COLUMN_REVIEW to review
            ),
            "${AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID} = ? AND ${AccountsFilmsReviewsTable.COLUMN_FILM_ID} = ?",
            arrayOf(idAccount.toString(), idFilm.toString())
        )
    }

    private fun addRating(idAccount: Long, idFilm: Long, rating: Int) {
        db.insert(
            AccountsFilmsReviewsTable.TABLE_NAME,
            null,
            contentValuesOf(
                AccountsFilmsReviewsTable.COLUMN_FILM_ID to idFilm,
                AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID to idAccount,
                AccountsFilmsReviewsTable.COLUMN_RATING to rating
            )
        )
    }

    private fun updateRating(idAccount: Long, idFilm: Long, rating: Int) {
        db.update(
            AccountsFilmsReviewsTable.TABLE_NAME,
            contentValuesOf(
                AccountsFilmsReviewsTable.COLUMN_RATING to rating
            ),
            "${AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID} = ? AND ${AccountsFilmsReviewsTable.COLUMN_FILM_ID} = ?",
            arrayOf(idAccount.toString(), idFilm.toString())
        )
    }

    private fun parseReview(cursor: Cursor): Review {
        return Review(
            idFilm = cursor.getLong(cursor.getColumnIndexOrThrow(AccountsFilmsReviewsTable.COLUMN_FILM_ID)),
            idAccount = cursor.getLong(cursor.getColumnIndexOrThrow(AccountsFilmsReviewsTable.COLUMN_ACCOUNT_ID)),
            rating = cursor.getIntOrNull(cursor.getColumnIndexOrThrow(AccountsFilmsReviewsTable.COLUMN_RATING)),
            review = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(AccountsFilmsReviewsTable.COLUMN_REVIEW))
        )
    }

}