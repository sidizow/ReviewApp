package com.example.catalog.domain.usecases

import com.example.catalog.domain.entities.Film
import com.example.catalog.domain.repositories.CatalogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListFilmsUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun getListFilms(): Flow<List<Film>> = catalogRepository.getListFilms()
}