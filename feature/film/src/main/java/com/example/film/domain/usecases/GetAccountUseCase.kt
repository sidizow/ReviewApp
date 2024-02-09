package com.example.film.domain.usecases

import com.example.film.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
){

    suspend fun getAccountId(): Flow<Long?> = accountRepository.getAccountId()

}