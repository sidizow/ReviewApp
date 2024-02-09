package com.example.film.domain.usecases

import com.example.film.domain.repositories.FilmRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateAvgUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {

    suspend fun updateAvg(filmId: Long, summaryScore: Double) {
        filmRepository.updateAvg(filmId, summaryScore)
    }

}