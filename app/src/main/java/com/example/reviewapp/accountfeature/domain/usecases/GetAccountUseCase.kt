package com.example.reviewapp.accountfeature.domain.usecases

import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository
){

    suspend fun getAccount(): Flow<Account?> = accountsRepository.getAccount()

}