package com.example.reviewapp.accountfeature.domain.usecases

import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import com.example.reviewapp.core.exceptions.EmptyFieldException
import com.example.reviewapp.core.constants.Field
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
) {

    suspend fun signIn(email: String, password: CharArray){
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isEmpty()) throw EmptyFieldException(Field.Password)

        accountsRepository.signIn(email, password)
    }


}