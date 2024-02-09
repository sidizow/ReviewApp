package com.example.reviewapp.glue.film

import com.example.film.domain.entities.Film
import com.example.film.domain.repositories.FilmRepository
import com.example.reviewapp.data.datasources.room.catalog.FilmsDao
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmDbEntity
import com.example.reviewapp.data.datasources.room.catalog.entities.FilmUpdateSummaryScoreTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterFilmRepository @Inject constructor(
    private val filmsDao: FilmsDao,
    private val ioDispatcher: CoroutineDispatcher
) : FilmRepository {

    override suspend fun getFilmById(filmId: Long): Flow<Film?> =
        withContext(ioDispatcher) {
            filmsDao.getFilmById(filmId).map { it?.toFilm() }
        }

    override suspend fun updateAvg(filmId: Long, summaryScore: Double) =
        withContext(ioDispatcher) {
            filmsDao.updateAvg(
                FilmUpdateSummaryScoreTuple(
                    id = filmId,
                    summaryScore = summaryScore
                )
            )
        }

    private fun FilmDbEntity.toFilm(): Film = Film(
        id = id,
        title = title,
        description = description,
        summaryScore = summaryScore,
        img = img
    )

}