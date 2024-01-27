package com.example.reviewapp.model.films

import com.example.reviewapp.model.films.entities.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    suspend fun getFlowFilms(): Flow<List<Film>>

    suspend fun getListFilms(): List<Film>

    suspend fun getFilmById(filmId: Long): Film?

    suspend fun updateAvg(filmId: Long, summaryScore: Double)

    suspend fun getSummaryScoreByFilmId(filmId: Long): Double?
}