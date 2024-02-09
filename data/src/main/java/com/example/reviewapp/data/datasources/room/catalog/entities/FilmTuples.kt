package com.example.reviewapp.data.datasources.room.catalog.entities

import androidx.room.ColumnInfo

data class FilmUpdateSummaryScoreTuple(
    val id: Long,
    @ColumnInfo(name = "summary_score") val summaryScore: Double,
)