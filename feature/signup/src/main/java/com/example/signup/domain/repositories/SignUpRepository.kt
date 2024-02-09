package com.example.signup.domain.repositories

import com.example.signup.domain.entities.SignUpData

interface SignUpRepository {

    suspend fun signUp(signUpData: SignUpData)

}