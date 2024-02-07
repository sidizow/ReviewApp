package com.example.reviewapp.data.datasources.room.catalog.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reviewapp.catalogfilmsfeature.domain.entities.Film

@Entity(
    tableName = "films"
)
data class FilmDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String,
    @ColumnInfo(name = "summary_score") val summaryScore: Double,
    val img: String
) {
    fun toFilm(): Film = Film(
        id = id,
        title = title,
        description = description,
        summaryScore = summaryScore,
        img = img
    )

}