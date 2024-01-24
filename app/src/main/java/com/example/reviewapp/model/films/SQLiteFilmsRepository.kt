package com.example.reviewapp.model.films

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.model.sqlite.AppSQLiteContract.FilmsTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SQLiteFilmsRepository @Inject constructor(
    private val db: SQLiteDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {

    override suspend fun getListFilms(): List<Film> = withContext(ioDispatcher){
        val cursor = db.rawQuery("SELECT * FROM ${FilmsTable.TABLE_NAME}", null)
        return@withContext cursor.use {
            val list = mutableListOf<Film>()
            while (cursor.moveToNext()) {
                list.add(parseFilm(cursor))
            }
            return@use list
        }
    }

    override suspend fun getFilmById(idFilm: Long): Film? = withContext(ioDispatcher){
        val cursor = db.query(
            FilmsTable.TABLE_NAME,
            arrayOf(
                FilmsTable.COLUMN_ID,
                FilmsTable.COLUMN_TITLE,
                FilmsTable.COLUMN_DESCRIPTION,
                FilmsTable.COLUMN_SUMMARY_SCORE,
                FilmsTable.COLUMN_IMG
            ),
            "${FilmsTable.COLUMN_ID} = ?",
            arrayOf(idFilm.toString()),
            null,null,null
        )
        return@withContext cursor.use {
            if(cursor.count == 0) return@use null
            cursor.moveToFirst()
            parseFilm(cursor)
        }
    }

    private fun parseFilm(cursor: Cursor): Film {
        return Film(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(FilmsTable.COLUMN_ID)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(FilmsTable.COLUMN_TITLE)),
            description = cursor.getString(cursor.getColumnIndexOrThrow(FilmsTable.COLUMN_DESCRIPTION)),
            summaryScore = cursor.getDouble(cursor.getColumnIndexOrThrow(FilmsTable.COLUMN_SUMMARY_SCORE)),
            img = cursor.getString(cursor.getColumnIndexOrThrow(FilmsTable.COLUMN_IMG))
        )
    }
}