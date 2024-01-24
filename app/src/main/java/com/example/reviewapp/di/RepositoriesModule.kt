package com.example.reviewapp.di

import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.SQLiteAccountsRepository
import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.SQLiteFilmsRepository
import com.example.reviewapp.model.reviews.ReviewsRepository
import com.example.reviewapp.model.reviews.SQLiteReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAccountRepository(
        sqLiteAccountsRepository: SQLiteAccountsRepository
    ): AccountsRepository

    @Binds
    abstract fun bindFilmRepository(
        sqLiteFilmsRepository: SQLiteFilmsRepository
    ): FilmsRepository

    @Binds
    abstract fun bindReviewRepository(
        sqLiteReviewsRepository: SQLiteReviewsRepository
    ): ReviewsRepository
}