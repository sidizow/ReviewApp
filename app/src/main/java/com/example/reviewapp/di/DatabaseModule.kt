package com.example.reviewapp.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.reviewapp.model.sqlite.AppSQLiteHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun bindDatabase(
        @ApplicationContext appContext: Context,
    ): SQLiteDatabase{
        return AppSQLiteHelper(appContext).writableDatabase
    }

}