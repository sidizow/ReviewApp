package com.example.reviewapp.glue.signin

import com.example.presentation.exceptions.AuthException
import com.example.presentation.utils.AsyncLoader
import com.example.presentation.utils.security.SecurityUtils
import com.example.reviewapp.data.datasources.room.accounts.AccountsDao
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.room.database.wrapSQLiteException
import com.example.reviewapp.data.datasources.sharedpref.settings.AppSettings
import com.example.signin.domain.entities.Account
import com.example.signin.domain.repositories.SignInRepository
import com.example.signup.domain.entities.SignUpData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterSignInRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings,
    private val securityUtils: SecurityUtils,
    private val ioDispatcher: CoroutineDispatcher
) : SignInRepository {

    private val currentAccountIdFlow = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }

    override suspend fun isSignedIn(): Boolean {
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: CharArray) =
        wrapSQLiteException(ioDispatcher) {
            val accountId = findAccountIdByEmailAndPassword(email, password)
            appSettings.setCurrentAccountId(accountId)
            currentAccountIdFlow.get().value = AccountId(accountId)
            return@wrapSQLiteException
        }


    override suspend fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow.get()
            .flatMapLatest { accountId ->
                getAccountById(accountId.value)
            }
            .flowOn(ioDispatcher)
    }

    private suspend fun findAccountIdByEmailAndPassword(email: String, password: CharArray): Long {
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()
        val saltBytes = securityUtils.stringToBytes(tuple.salt)
        val hashByte = securityUtils.passwordToHash(password, saltBytes)
        val hashString = securityUtils.bytesToString(hashByte)
        password.fill('*')
        if (tuple.hash != hashString) throw AuthException()
        return tuple.id
    }

    private fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getAccountById(accountId)
            .map { accountDbEntity ->
                accountDbEntity?.toAccount()
            }
    }

    private class AccountId(val value: Long)

    private fun AccountDbEntity.toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )
    companion object {
        fun fromSignUpData(signUpData: SignUpData, securityUtils: SecurityUtils): AccountDbEntity {
            val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password, salt)
            signUpData.password.fill('*')
            signUpData.repeatPassword.fill('*')
            return AccountDbEntity(
                id = 0,
                email = signUpData.email,
                username = signUpData.username,
                hash = securityUtils.bytesToString(hash),
                salt = securityUtils.bytesToString(salt),
                createdAt = System.currentTimeMillis()
            )
        }

    }



}

