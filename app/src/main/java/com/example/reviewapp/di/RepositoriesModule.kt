package com.example.reviewapp.di

import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import com.example.reviewapp.catalogfilmsfeature.domain.repositories.FilmsRepository
import com.example.reviewapp.data.repositories.RoomAccountsRepository
import com.example.reviewapp.data.repositories.RoomFilmsRepository
import com.example.reviewapp.data.repositories.RoomReviewsRepository
import com.example.reviewapp.filmfeature.domain.repositories.ReviewsRepository
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