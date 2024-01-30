package com.example.reviewapp.di

import android.content.Context
import androidx.room.Room
import com.example.reviewapp.model.accounts.room.AccountsDao
import com.example.reviewapp.model.films.room.FilmsDao
import com.example.reviewapp.model.reviews.room.ReviewsDao
import com.example.reviewapp.model.room.AppDatabase
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "reviews.db"
        ).createFromAsset("initial.db").build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: AppDatabase): AccountsDao {
        return database.getAccountsDao()
    }

    @Provides
    @Singleton
    fun provideFilmsDao(database: AppDatabase): FilmsDao {
        return database.getFilmsDao()
    }

    @Provides
    @Singleton
    fun provideReviewsDao(database: AppDatabase): ReviewsDao {
        return database.getReviewsDao()
    }

}