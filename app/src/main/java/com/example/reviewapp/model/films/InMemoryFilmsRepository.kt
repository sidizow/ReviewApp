package com.example.reviewapp.model.films

import com.example.reviewapp.model.films.entities.Film
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryFilmsRepository @Inject constructor() : FilmsRepository {



    private val films = mutableListOf(
        Film(
            id = 1L,
            title = "Зеленая миля",
            description = "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в страшном преступлении, стал одним из самых необычных обитателей блока.",
            summaryScore = 0.0,
            img = IMAGES[0]
        ),
        Film(
            id = 2L,
            title = "Побег из Шоушенка",
            description = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения.",
            summaryScore = 0.0,
            img = IMAGES[1]
            )
    )


    override suspend fun getListFilms(): List<Film> = films.map { it.copy() }

    override suspend fun getFilmById(idFilm: Long) = films.firstOrNull { it.id == idFilm }


    companion object{
        private val IMAGES = mutableListOf(
            "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/acb932eb-c7d0-42de-92df-f5f306c4c48e/1920x",
            "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/e26044e5-2d5a-4b38-a133-a776ad93366f/1920x",
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"
        )
    }
}