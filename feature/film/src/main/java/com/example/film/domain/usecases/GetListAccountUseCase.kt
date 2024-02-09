package com.example.film.domain.usecases

import com.example.film.domain.entities.Account
import com.example.film.domain.repositories.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListAccountUseCase @Inject constructor(
    private val accountsRepository: AccountRepository
){

    suspend fun getListAccount(): List<Account> = accountsRepository.getListAccount()

}