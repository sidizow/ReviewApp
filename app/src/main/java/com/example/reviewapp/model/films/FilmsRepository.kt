package com.example.reviewapp.model.films

import com.example.reviewapp.model.films.entities.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    suspend fun getListFilms(): Flow<List<Film>>

    suspend fun getFilmById(filmId: Long): Flow<Film?>

    suspend fun updateAvg(filmId: Long, summaryScore: Double)

}