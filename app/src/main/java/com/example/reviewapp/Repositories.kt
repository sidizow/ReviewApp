package com.example.reviewapp

import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.InMemoryAccountsRepository

object Repositories {
    val accountRepository: AccountsRepository = InMemoryAccountsRepository()
}