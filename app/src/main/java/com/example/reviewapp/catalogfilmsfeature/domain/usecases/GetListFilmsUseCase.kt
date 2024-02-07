package com.example.reviewapp.catalogfilmsfeature.domain.usecases

import com.example.reviewapp.catalogfilmsfeature.domain.entities.Film
import com.example.reviewapp.catalogfilmsfeature.domain.repositories.FilmsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListFilmsUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {
    suspend fun getListFilms(): Flow<List<Film>> = filmsRepository.getListFilms()
}