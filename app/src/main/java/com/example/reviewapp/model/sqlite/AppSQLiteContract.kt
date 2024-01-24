package com.example.reviewapp.model.sqlite

object AppSQLiteContract {
    object AccountsTable {
        const val TABLE_NAME = "accounts"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_CREATED_AT = "created_at"
    }
    object FilmsTable {
        const val TABLE_NAME = "films"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_SUMMARY_SCORE = "summary_score"
        const val COLUMN_IMG = "img"
    }
    object AccountsFilmsReviewsTable {
        const val TABLE_NAME = "accounts_films_reviews"
        const val COLUMN_ACCOUNT_ID = "account_id"
        const val COLUMN_FILM_ID = "film_id"
        const val COLUMN_RATING = "rating"
        const val COLUMN_REVIEW = "review"
    }

}
