package com.example.reviewapp.model.films.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.reviewapp.model.films.room.entities.FilmDbEntity
import com.example.reviewapp.model.films.room.entities.FilmUpdateSummaryScoreTuple

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films WHERE id = :filmId")
    fun getFilmById(filmId: Long): FilmDbEntity?

    @Query("SELECT * FROM films")
    fun getAllFilms(): List<FilmDbEntity>


    @Update(entity = FilmDbEntity::class)
    fun updateAvg(film: FilmUpdateSummaryScoreTuple)

}