package com.example.signin.domain.usecases

import com.example.signin.domain.repositories.SignInRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsSignedInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
){

    suspend fun isSignedIn(): Boolean = signInRepository.isSignedIn()

}