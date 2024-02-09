package com.example.catalog.domain.repositories

import com.example.catalog.domain.entities.Film
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    suspend fun getListFilms(): Flow<List<Film>>

}