package com.example.reviewapp.model.films.room

import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.model.films.room.entities.FilmUpdateSummaryScoreTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomFilmsRepository @Inject constructor(
    private val filmsDao: FilmsDao,
    private val ioDispatcher: CoroutineDispatcher
) : FilmsRepository {

    private val _flowFilms = MutableStateFlow(listOf<Film>())

    override suspend fun getFlowFilms(): Flow<List<Film>>{
        _flowFilms.value = getListFilms()
        return _flowFilms
    }

    override suspend fun getListFilms(): List<Film> = withContext(ioDispatcher){
        filmsDao.getAllFilms().map { it.toFilm() }
    }

    override suspend fun getFilmById(filmId: Long): Film? = withContext(ioDispatcher) {
        filmsDao.getFilmById(filmId)?.toFilm()
    }

    override suspend fun getSummaryScoreByFilmId(filmId: Long): Double? =
        getFilmById(filmId)?.summaryScore


    override suspend fun updateAvg(filmId: Long, summaryScore: Double):Unit = withContext(ioDispatcher) {
        filmsDao.updateAvg(FilmUpdateSummaryScoreTuple(id = filmId, summaryScore = summaryScore))
        updateFilmsFlow()
    }

    private suspend fun updateFilmsFlow(){
        _flowFilms.value = getListFilms()
    }


}