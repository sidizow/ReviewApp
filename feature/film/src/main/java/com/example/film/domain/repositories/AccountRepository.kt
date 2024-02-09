package com.example.film.domain.repositories


import com.example.film.domain.entities.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun getListAccount(): List<Account>

    suspend fun getAccountId(): Flow<Long>

}