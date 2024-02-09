package com.example.reviewapp.glue.catalog

import com.example.catalog.domain.entities.Film
import com.example.catalog.domain.repositories.CatalogRepository
import com.example.reviewapp.data.datasources.room.catalog.FilmsDao
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterCatalogRepository @Inject constructor(
    private val filmsDao: FilmsDao,
    private val ioDispatcher: CoroutineDispatcher
) : CatalogRepository {

    override suspend fun getListFilms(): Flow<List<Film>> = withContext(ioDispatcher){
        filmsDao.getAllFilms().map { filmDbEntity -> filmDbEntity.map { it.toFilm() } }
    }

    private fun FilmDbEntity.toFilm(): Film = Film(
        id = id,
        title = title,
        description = description,
        summaryScore = summaryScore,
        img = img
    )

}