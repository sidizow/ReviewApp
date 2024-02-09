package com.example.signin.domain.usecases

import com.example.signin.domain.entities.Account
import com.example.signin.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountUseCase @Inject constructor(
    private val signInRepository: SignInRepository
){

    suspend fun getAccount(): Flow<Account?> = signInRepository.getAccount()

}