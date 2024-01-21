package com.example.reviewapp.model.reviews

import com.example.reviewapp.model.reviews.entities.Review
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryReviewsRepository @Inject constructor() : ReviewsRepository {


    private var reviews = mutableListOf(
       /* Review(
            idFilm = 1,
            idAccount = 1,
            rating = 10,
            review = null
        ),
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "Побег из Шоушенка» - экранизация романа Стивена Кинга. На мой взгляд, это редчайший случай, когда фильм превзошел литературное произведение, по которому он был создан. И, несмотря на то, что ставить картину по книге всегда сложно, это тот пример, когда фильм не только прекрасно передал на экране всю глубину, атмосферу и смысловой стержень замечательной книги Кинга, но и оказался сильнее, превзойдя оригинал"
        ),
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "Побег из Шоушенка» - экранизация романа Стивена Кинга. На мой взгляд, это редчайший случай, когда фильм превзошел литературное произведение, по которому он был создан. И, несмотря на то, что ставить картину по книге всегда сложно, это тот пример, когда фильм не только прекрасно передал на экране всю глубину, атмосферу и смысловой стержень замечательной книги Кинга, но и оказался сильнее, превзойдя оригинал"
        ),
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "Побег из Шоушенка» - экранизация романа Стивена Кинга. На мой взгляд, это редчайший случай, когда фильм превзошел литературное произведение, по которому он был создан. И, несмотря на то, что ставить картину по книге всегда сложно, это тот пример, когда фильм не только прекрасно передал на экране всю глубину, атмосферу и смысловой стержень замечательной книги Кинга, но и оказался сильнее, превзойдя оригинал"
        ),
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "Побег из Шоушенка» - экранизация романа Стивена Кинга. На мой взгляд, это редчайший случай, когда фильм превзошел литературное произведение, по которому он был создан. И, несмотря на то, что ставить картину по книге всегда сложно, это тот пример, когда фильм не только прекрасно передал на экране всю глубину, атмосферу и смысловой стержень замечательной книги Кинга, но и оказался сильнее, превзойдя оригинал"
        ),
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "Побег из Шоушенка» - экранизация романа Стивена Кинга. На мой взгляд, это редчайший случай, когда фильм превзошел литературное произведение, по которому он был создан. И, несмотря на то, что ставить картину по книге всегда сложно, это тот пример, когда фильм не только прекрасно передал на экране всю глубину, атмосферу и смысловой стержень замечательной книги Кинга, но и оказался сильнее, превзойдя оригинал"
        ),*/
        Review(
            idFilm = 1,
            idAccount = 2,
            rating = 8,
            review = "OK"
        ),
        Review(
            idFilm = 2,
            idAccount = 1,
            rating = 10,
            review = "Ок adsssssssssssssssss sdad"
        )

    )


    /*fun getReviewForCurrentAccount(idAccount: Long, idFilm: Long): Review? =
       reviews.firstOrNull { it.idAccount == idAccount && it.idFilm == idFilm}*/

    override suspend fun getReview(): List<Review> = reviews

    override suspend fun getIndexReviewByIdFilmAndIdAccount(idFilm: Long, idAccount: Long): Int {
        val review = reviews.firstOrNull { it.idAccount == idAccount && it.idFilm == idFilm }
        return reviews.indexOf(review)
    }

    override suspend fun getReviewByIdFilm(idFilm: Long): List<Review> =
        reviews.filter { it.idFilm == idFilm }

    override suspend fun selectRatingFilm(idAccount: Long, idFilm: Long, rating: Int) {
        val index = getIndexReviewByIdFilmAndIdAccount(idFilm, idAccount)
        reviews.map { it.copy() }
        if (index == -1) {
            reviews.add(
                Review(
                    idAccount = idAccount,
                    idFilm = idFilm,
                    rating = rating,
                    review = null
                )
            )
        } else {
            reviews[index].rating = rating
        }

    }

    override suspend fun addReviewForFilm(idAccount: Long, idFilm: Long, review: String) {
        val index = getIndexReviewByIdFilmAndIdAccount(idFilm, idAccount)
        reviews.map { it.copy() }
        if (index == -1) {
            reviews.add(
                Review(
                    idAccount = idAccount,
                    idFilm = idFilm,
                    rating = null,
                    review = review
                )
            )
        } else {
            reviews[index].review = review
        }
    }
}