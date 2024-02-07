package com.example.reviewapp.catalogfilmsfeature.domain.usecases

import com.example.reviewapp.catalogfilmsfeature.domain.repositories.FilmsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateAvgUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository,
) {

    suspend fun updateAvg(filmId: Long, summaryScore: Double) {
        filmsRepository.updateAvg(filmId, summaryScore)
    }

}