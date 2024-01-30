package com.example.reviewapp.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reviewapp.model.accounts.room.AccountsDao
import com.example.reviewapp.model.accounts.room.entities.AccountDbEntity
import com.example.reviewapp.model.films.room.FilmsDao
import com.example.reviewapp.model.films.room.entities.FilmDbEntity
import com.example.reviewapp.model.reviews.room.ReviewsDao
import com.example.reviewapp.model.reviews.room.entities.ReviewDbEntity

@Database(
    version = 2,
    entities = [
        AccountDbEntity::class,
        FilmDbEntity::class,
        ReviewDbEntity::class
    ],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoMigrationSpec1to2::class
        )
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getFilmsDao(): FilmsDao

    abstract fun getReviewsDao(): ReviewsDao

}