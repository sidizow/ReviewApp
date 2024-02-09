package com.example.film.domain.usecases


import com.example.film.domain.entities.Film
import com.example.film.domain.repositories.FilmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFilmByIdUseCase @Inject constructor(
    private val filmRepository: FilmRepository
) {

    suspend fun getFilmById(filmId: Long): Flow<Film?> = filmRepository.getFilmById(filmId)

}