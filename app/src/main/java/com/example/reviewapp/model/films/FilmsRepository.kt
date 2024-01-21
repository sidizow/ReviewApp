package com.example.reviewapp.model.films

import com.example.reviewapp.model.films.entities.Film

interface FilmsRepository {
    suspend fun getListFilms(): List<Film>

    suspend fun getFilmById(idFilm: Long): Film?
}