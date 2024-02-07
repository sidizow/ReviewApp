package com.example.reviewapp.data.repositories

import android.database.sqlite.SQLiteConstraintException
import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.accountfeature.domain.entities.SignUpData
import com.example.reviewapp.accountfeature.domain.repositories.AccountsRepository
import com.example.reviewapp.data.datasources.room.accounts.AccountsDao
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.room.database.wrapSQLiteException
import com.example.reviewapp.data.datasources.sharedpref.settings.AppSettings
import com.example.reviewapp.core.exceptions.AccountAlreadyExistsException
import com.example.reviewapp.core.exceptions.AuthException
import com.example.reviewapp.core.utils.AsyncLoader
import com.example.reviewapp.core.utils.security.SecurityUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomAccountsRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings,
    private val securityUtils: SecurityUtils,
    private val ioDispatcher: CoroutineDispatcher
) : AccountsRepository {

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

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher) {
        createAccount(signUpData)
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

    override suspend fun getListAccount(): List<Account> = withContext(ioDispatcher) {
        return@withContext accountsDao.getAllAccounts().map { it.toAccount() }
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

    private suspend fun createAccount(signUpData: SignUpData) {
        try {
            val entity = AccountDbEntity.fromSignUpData(signUpData, securityUtils)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val appException = AccountAlreadyExistsException()
            appException.initCause(e)
            throw appException
        }
    }

    private fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getAccountById(accountId)
            .map { accountDbEntity ->
                accountDbEntity?.toAccount()
            }
    }


    private class AccountId(val value: Long)
}

