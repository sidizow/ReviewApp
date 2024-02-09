package com.example.film.domain.repositories


import com.example.film.domain.entities.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    suspend fun getFilmById(filmId: Long): Flow<Film?>

    suspend fun updateAvg(filmId: Long, summaryScore: Double)

}