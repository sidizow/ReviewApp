package com.example.reviewapp.accountfeature.domain.repositories

import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.accountfeature.domain.entities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: CharArray)

    suspend fun signUp(signUpData: SignUpData)

    suspend fun getListAccount(): List<Account>

    suspend fun logout()

    suspend fun getAccount(): Flow<Account?>


}