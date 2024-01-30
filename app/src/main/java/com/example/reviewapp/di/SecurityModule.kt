package com.example.reviewapp.di

import com.example.reviewapp.utils.security.DefaultSecurityUtilsImpl
import com.example.reviewapp.utils.security.SecurityUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    abstract fun bindSecurity(
        defaultSecurityUtilsImpl: DefaultSecurityUtilsImpl
    ): SecurityUtils

}