package com.example.reviewapp.model.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSQLiteHelper @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) : SQLiteOpenHelper(applicationContext, "reviews.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = applicationContext.assets.open("db_init.sql").bufferedReader().use {
            it.readText()
        }
        sql.split(';')
            .filter { it.isNotBlank() }
            .forEach{
                db.execSQL(it)
            }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}