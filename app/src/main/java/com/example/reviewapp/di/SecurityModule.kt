package com.example.reviewapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    abstract fun bindSecurity(
        defaultSecurityUtilsImpl: com.example.presentation.utils.security.DefaultSecurityUtilsImpl
    ): com.example.presentation.utils.security.SecurityUtils

}