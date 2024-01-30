package com.example.reviewapp.di

import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.room.RoomAccountsRepository
import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.room.RoomFilmsRepository
import com.example.reviewapp.model.reviews.ReviewsRepository
import com.example.reviewapp.model.reviews.room.RoomReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAccountRepository(
        roomAccountsRepository: RoomAccountsRepository
    ): AccountsRepository

    @Binds
    abstract fun bindFilmRepository(
        roomFilmsRepository: RoomFilmsRepository
    ): FilmsRepository

    @Binds
    abstract fun bindReviewRepository(
        roomReviewsRepository: RoomReviewsRepository
    ): ReviewsRepository
}