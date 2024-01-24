package com.example.reviewapp.model.films

import com.example.reviewapp.model.films.entities.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    suspend fun getFlowFilms(): Flow<List<Film>>
    suspend fun getListFilms(): List<Film>

    suspend fun getFilmById(idFilm: Long): Film?

    suspend fun updateAVG(idFilm: Long, summaryScore: Double)

    suspend fun getSummaryScoreByIdFilm(idFilm: Long): Double?
}