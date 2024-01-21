package com.example.reviewapp.di

import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.InMemoryAccountsRepository
import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.InMemoryFilmsRepository
import com.example.reviewapp.model.reviews.InMemoryReviewsRepository
import com.example.reviewapp.model.reviews.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModel {

    @Binds
    abstract fun bindAccountRepository(
        inMemoryAccountsRepository: InMemoryAccountsRepository
    ): AccountsRepository

    @Binds
    abstract fun bindFilmRepository(
        inMemoryFilmsRepository: InMemoryFilmsRepository
    ): FilmsRepository

    @Binds
    abstract fun bindReviewRepository(
        iinMemoryReviewsRepository: InMemoryReviewsRepository
    ): ReviewsRepository
}