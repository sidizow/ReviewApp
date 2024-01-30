package com.example.reviewapp.model.accounts.room.entities

import androidx.room.ColumnInfo

data class AccountSignInTuple(
    val id: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt") val salt: String
)
