package com.example.signin.domain.usecases

import com.example.presentation.constants.Field
import com.example.presentation.exceptions.EmptyFieldException
import com.example.signin.domain.repositories.SignInRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {

    suspend fun signIn(email: String, password: CharArray){
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isEmpty()) throw EmptyFieldException(Field.Password)

        signInRepository.signIn(email, password)
    }


}