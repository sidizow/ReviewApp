package com.example.signup.domain.usecases

import com.example.signup.domain.entities.SignUpData
import com.example.signup.domain.repositories.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
){

    suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        signUpRepository.signUp(signUpData)
    }

}