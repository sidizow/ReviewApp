package com.example.reviewapp.data.datasources.sharedpref.settings

interface AppSettings {

    fun getCurrentAccountId(): Long

    fun setCurrentAccountId(accountId: Long)

    companion object {
        const val NO_ACCOUNT_ID = -1L
    }

}