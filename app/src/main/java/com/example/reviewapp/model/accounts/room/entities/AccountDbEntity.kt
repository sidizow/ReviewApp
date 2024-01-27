package com.example.reviewapp.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.accounts.entities.SignUpData

/*
"id"		INTEGER PRIMARY KEY,
"email" 	TEXT NOT NULL UNIQUE COLLATE NOCASE,
"username" 	TEXT NOT NULL UNIQUE,
"password"  TEXT NOT NULL,
"created_at" INTEGER NOT NULL
*/

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true),
        Index("username", unique = true)
    ]
)
data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    val username: String,
    val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {

    fun toAccount() : Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object{
        fun fromSignUpData(signUpData: SignUpData): AccountDbEntity = AccountDbEntity(
            id = 0,
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password,
            createdAt = System.currentTimeMillis()
        )

    }


}