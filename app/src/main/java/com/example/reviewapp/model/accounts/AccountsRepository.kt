package com.example.reviewapp.model.accounts

import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.accounts.entities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(signUpData: SignUpData)

    suspend fun getListAccount(): List<Account>

    fun logout()

    fun getAccount(): Flow<Account?>


}