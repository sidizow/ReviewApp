package com.example.reviewapp.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.accounts.entities.SignUpData
import com.example.reviewapp.utils.security.SecurityUtils

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
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt", defaultValue = "") val salt: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
) {

    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData, securityUtils: SecurityUtils): AccountDbEntity {
            val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password, salt)
            signUpData.password.fill('*')
            signUpData.repeatPassword.fill('*')
            return AccountDbEntity(
                id = 0,
                email = signUpData.email,
                username = signUpData.username,
                hash = securityUtils.bytesToString(hash),
                salt = securityUtils.bytesToString(salt),
                createdAt = System.currentTimeMillis()
            )
        }


    }


}