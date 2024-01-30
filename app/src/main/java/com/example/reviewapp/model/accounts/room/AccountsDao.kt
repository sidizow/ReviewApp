package com.example.reviewapp.model.accounts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.reviewapp.model.accounts.room.entities.AccountDbEntity
import com.example.reviewapp.model.accounts.room.entities.AccountSignInTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT id, hash, salt FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Insert
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountById(accountId: Long): Flow<AccountDbEntity?>

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): List<AccountDbEntity>

}