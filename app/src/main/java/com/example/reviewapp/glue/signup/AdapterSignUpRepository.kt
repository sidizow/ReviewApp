package com.example.reviewapp.glue.signup

import android.database.sqlite.SQLiteConstraintException
import com.example.presentation.exceptions.AccountAlreadyExistsException
import com.example.presentation.utils.security.SecurityUtils
import com.example.reviewapp.data.datasources.room.accounts.AccountsDao
import com.example.reviewapp.data.datasources.room.database.wrapSQLiteException
import com.example.reviewapp.glue.signin.AdapterSignInRepository
import com.example.signup.domain.entities.SignUpData
import com.example.signup.domain.repositories.SignUpRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AdapterSignUpRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val securityUtils: SecurityUtils,
    private val ioDispatcher: CoroutineDispatcher
): SignUpRepository {

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher) {
        createAccount(signUpData)
    }

    private suspend fun createAccount(signUpData: SignUpData) {
        try {
            val entity = AdapterSignInRepository.fromSignUpData(signUpData, securityUtils)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val appException = AccountAlreadyExistsException()
            appException.initCause(e)
            throw appException
        }
    }
}