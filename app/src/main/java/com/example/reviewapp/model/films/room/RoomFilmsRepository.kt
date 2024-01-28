package com.example.reviewapp.model.films.room

import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.model.films.room.entities.FilmUpdateSummaryScoreTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomFilmsRepository @Inject constructor(
    private val filmsDao: FilmsDao,
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {

    override suspend fun getListFilms(): Flow<List<Film>> = withContext(ioDispatcher){
        filmsDao.getAllFilms().map { filmDbEntity -> filmDbEntity.map { it.toFilm() } }
    }

    override suspend fun getFilmById(filmId: Long): Flow<Film?> = withContext(ioDispatcher) {
        filmsDao.getFilmById(filmId).map { it?.toFilm() }
    }

    override suspend fun updateAvg(filmId: Long, summaryScore: Double):Unit = withContext(ioDispatcher) {
        filmsDao.updateAvg(FilmUpdateSummaryScoreTuple(id = filmId, summaryScore = summaryScore))
    }

}