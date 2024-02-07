package com.example.reviewapp.data.datasources.room.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reviewapp.data.datasources.room.catalog.FilmsDao
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity
import com.example.reviewapp.data.datasources.room.accounts.AccountsDao
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.room.reviews.ReviewsDao
import com.example.reviewapp.data.datasources.room.reviews.entities.ReviewDbEntity

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
    ],
    exportSchema = true
)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getFilmsDao(): FilmsDao

    abstract fun getReviewsDao(): ReviewsDao

}