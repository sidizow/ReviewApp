package com.example.reviewapp.data.datasources.room.reviews.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity
import com.example.reviewapp.filmfeature.domain.entities.Review


//TODO мб надо добавить unique для составного ключа
@Entity(
    tableName = "accounts_films_reviews",
    primaryKeys = ["account_id", "film_id"],
    indices = [
        Index("film_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = AccountDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FilmDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["film_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ReviewDbEntity(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "film_id") val filmId: Long,
    val rating: Int?,
    val review: String?
) {

    fun toReview(): Review = Review(
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