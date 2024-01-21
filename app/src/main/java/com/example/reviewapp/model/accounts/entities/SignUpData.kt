package com.example.reviewapp.model.accounts.entities

import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.PasswordMismatchException

data class SignUpData(
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
) {
    fun validate() {
        if(username.isBlank()) throw EmptyFieldException(Field.Username)
        if(email.isBlank()) throw EmptyFieldException(Field.Email)
        if(password.isBlank()) throw EmptyFieldException(Field.Password)
        if(repeatPassword != password) throw PasswordMismatchException()
    }
}
