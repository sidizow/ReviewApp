package com.example.signin.domain.repositories

import com.example.signin.domain.entities.Account
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: CharArray)

    suspend fun logout()

    suspend fun getAccount(): Flow<Account?>


}