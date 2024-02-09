package com.example.reviewapp.data.datasources.room.reviews.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity


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
)