package com.example.reviewapp.di

import com.example.catalog.domain.repositories.CatalogRepository
import com.example.film.domain.repositories.AccountRepository
import com.example.film.domain.repositories.FilmRepository
import com.example.film.domain.repositories.ReviewsRepository
import com.example.reviewapp.glue.catalog.AdapterCatalogRepository
import com.example.reviewapp.glue.film.AdapterAccountRepository
import com.example.reviewapp.glue.film.AdapterFilmRepository
import com.example.reviewapp.glue.film.AdapterReviewsRepository
import com.example.reviewapp.glue.signin.AdapterSignInRepository
import com.example.reviewapp.glue.signup.AdapterSignUpRepository
import com.example.signin.domain.repositories.SignInRepository
import com.example.signup.domain.repositories.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAdapterSignInRepository(
        adapterSignInRepository: AdapterSignInRepository
    ): SignInRepository

    @Binds
    abstract fun bindAdapterCatalogRepository(
        adapterCatalogRepository: AdapterCatalogRepository
    ): CatalogRepository

    @Binds
    abstract fun bindAdapterReviewRepository(
        adapterReviewsRepository: AdapterReviewsRepository
    ): ReviewsRepository

    @Binds
    abstract fun bindAdapterFilmRepository(
        adapterFilmRepository: AdapterFilmRepository
    ):FilmRepository

    @Binds
    abstract fun bindAdapterAccountRepository(
        adapterAccountRepository: AdapterAccountRepository
    ):AccountRepository

    @Binds
    abstract fun bindAdapterSignUpRepository(
        adapterSignUpRepository: AdapterSignUpRepository
    ): SignUpRepository

}