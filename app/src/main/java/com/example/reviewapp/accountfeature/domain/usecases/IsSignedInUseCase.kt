package com.example.reviewapp.accountfeature.domain.usecases

import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsSignedInUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository
){

    suspend fun isSignedIn(): Boolean = accountsRepository.isSignedIn()

}