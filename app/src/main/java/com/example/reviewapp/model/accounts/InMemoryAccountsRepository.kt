package com.example.reviewapp.model.accounts

import com.example.reviewapp.model.AccountAlreadyExistsException
import com.example.reviewapp.model.AuthException
import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.accounts.entities.SignUpData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryAccountsRepository @Inject constructor(): AccountsRepository {

    private var currentAccountFlow = MutableStateFlow<Account?>(null)

    private val accounts = mutableListOf(
        AccountRecord(
            account = Account(
                id = 1,
                username = "admin",
                email = "admin@gmail.com"
            ),
            password = "123"
        ),
        AccountRecord(
            account = Account(
                id = 2,
                username = "user",
                email = "user@gmail.com"
            ),
            password = "123"
        )
    )

    init {
        currentAccountFlow.value = accounts[0].account
    }

    override suspend fun isSignedIn(): Boolean {
        return currentAccountFlow.value != null
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        val record = getRecordByEmail(email)
        if (record != null && record.password == password) {
            currentAccountFlow.value = record.account
        } else {
            throw AuthException()
        }
    }

    override suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()

        val accountRecord = getRecordByEmail(signUpData.email)
        if (accountRecord != null) throw AccountAlreadyExistsException()

        val newAccount = Account(
            id = (2..10).random().toLong(),
            username = signUpData.username,
            email = signUpData.email,
            createdAt = System.currentTimeMillis()
        )
        accounts.add(AccountRecord(newAccount, signUpData.password))
    }

    override fun logout() {
        currentAccountFlow.value = null
    }

    override fun getAccount(): Flow<Account?> = currentAccountFlow

    override suspend fun getListAccount(): List<Account> {
        val account = mutableListOf<Account>()
        accounts.forEach{ account.add(it.account) }
        return account
    }

    private fun getRecordByEmail(email: String) = accounts.firstOrNull { it.account.email == email }
    private class AccountRecord(
        val account: Account,
        val password: String,
    )
}