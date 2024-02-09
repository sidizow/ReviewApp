package com.example.reviewapp.data.datasources.room.accounts.entities

import androidx.room.ColumnInfo

data class AccountSignInTuple(
    val id: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt") val salt: String
)
