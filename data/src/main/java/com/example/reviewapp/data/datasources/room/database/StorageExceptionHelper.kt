package com.example.reviewapp.data.datasources.room.database

import android.database.sqlite.SQLiteException
import com.example.presentation.exceptions.StorageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): T {
    try {
        return withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}