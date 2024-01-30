package com.example.reviewapp.model.accounts.entities

import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.PasswordMismatchException

data class SignUpData(
    val username: String,
    val email: String,
    val password: CharArray,
    val repeatPassword: CharArray,
) {
    fun validate() {
        if(username.isBlank()) throw EmptyFieldException(Field.Username)
        if(email.isBlank()) throw EmptyFieldException(Field.Email)
        if(password.isEmpty()) throw EmptyFieldException(Field.Password)
        if(!repeatPassword.contentEquals(password)) throw PasswordMismatchException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpData

        if (username != other.username) return false
        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false
        return repeatPassword.contentEquals(other.repeatPassword)
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + repeatPassword.contentHashCode()
        return result
    }
}
