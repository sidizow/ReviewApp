package com.example.reviewapp.model.films.room.entities

import androidx.room.ColumnInfo

data class FilmUpdateSummaryScoreTuple(
    val id: Long,
    @ColumnInfo(name = "summary_score") val summaryScore: Double,
)