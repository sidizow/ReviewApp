package com.example.reviewapp.accountfeature.domain.usecases

import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListAccountUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository
){

    suspend fun getListAccount(): List<Account> = accountsRepository.getListAccount()

}