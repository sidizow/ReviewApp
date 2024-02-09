package com.example.reviewapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): com.example.reviewapp.data.datasources.room.database.AppDatabase {
        return Room.databaseBuilder(
            appContext,
            com.example.reviewapp.data.datasources.room.database.AppDatabase::class.java, "reviews.db"
        ).createFromAsset("initial.db").build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: com.example.reviewapp.data.datasources.room.database.AppDatabase): com.example.reviewapp.data.datasources.room.accounts.AccountsDao {
        return database.getAccountsDao()
    }

    @Provides
    @Singleton
    fun provideFilmsDao(database: com.example.reviewapp.data.datasources.room.database.AppDatabase): com.example.reviewapp.data.datasources.room.catalog.FilmsDao {
        return database.getFilmsDao()
    }

    @Provides
    @Singleton
    fun provideReviewsDao(database: com.example.reviewapp.data.datasources.room.database.AppDatabase): com.example.reviewapp.data.datasources.room.reviews.ReviewsDao {
        return database.getReviewsDao()
    }

}