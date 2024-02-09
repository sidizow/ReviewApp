package com.example.reviewapp.glue.film

import com.example.film.domain.entities.Account
import com.example.film.domain.repositories.AccountRepository
import com.example.reviewapp.data.datasources.room.accounts.AccountsDao
import com.example.reviewapp.data.datasources.room.accounts.entities.AccountDbEntity
import com.example.reviewapp.data.datasources.sharedpref.settings.AppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterAccountRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher,
) : AccountRepository {

    private val currentAccountIdFlow = MutableStateFlow(0L)

    override suspend fun getListAccount(): List<Account> = withContext(ioDispatcher) {
        return@withContext accountsDao.getAllAccounts().map { it.toAccount() }
    }

    override suspend fun getAccountId(): Flow<Long> {
        currentAccountIdFlow.value = appSettings.getCurrentAccountId()
        return currentAccountIdFlow
    }

    private fun AccountDbEntity.toAccount(): Account =
        Account(
            id = id,
            username = username
        )

}