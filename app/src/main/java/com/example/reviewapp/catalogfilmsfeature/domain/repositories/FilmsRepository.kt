package com.example.reviewapp.catalogfilmsfeature.domain.repositories

import com.example.reviewapp.catalogfilmsfeature.domain.entities.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    suspend fun getListFilms(): Flow<List<Film>>

    suspend fun getFilmById(filmId: Long): Flow<Film?>

    suspend fun updateAvg(filmId: Long, summaryScore: Double)

}