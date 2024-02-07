package com.example.reviewapp.di

import com.example.reviewapp.data.datasources.sharedpref.settings.AppSettings
import com.example.reviewapp.data.datasources.sharedpref.settings.SharedPreferencesAppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {

    @Binds
    abstract fun bindAppSettings(
        appSettings: SharedPreferencesAppSettings
    ): AppSettings

}