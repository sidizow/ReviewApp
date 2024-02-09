package com.example.reviewapp.data.datasources.room.catalog

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmUpdateSummaryScoreTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films WHERE id = :filmId")
    fun getFilmById(filmId: Long): Flow<FilmDbEntity?>

    @Query("SELECT * FROM films")
    fun getAllFilms(): Flow<List<FilmDbEntity>>


    @Update(entity = FilmDbEntity::class)
    fun updateAvg(film: FilmUpdateSummaryScoreTuple)

}