package com.example.reviewapp.accountfeature.domain.usecases

import com.example.reviewapp.accountfeature.domain.entities.SignUpData
import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository
){

    suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        accountsRepository.signUp(signUpData)
    }

}