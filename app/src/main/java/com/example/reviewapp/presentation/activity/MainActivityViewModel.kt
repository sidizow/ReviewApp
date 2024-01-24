package com.example.reviewapp.presentation.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.utils.MutableLiveEvent
import com.example.reviewapp.utils.publishEvent
import com.example.reviewapp.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    private val _restartFromLoginEvent = MutableLiveEvent<Unit>()
    val restartWithSignInEvent = _restartFromLoginEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(accountsRepository.isSignedIn())
            accountsRepository.getAccount().collect {
                if (it == null) {
                    _username.value = ""
                } else {
                    _username.value = "@${it.username}"
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        accountsRepository.logout()
        restartAppFromLoginScreen()
    }
    private fun restartAppFromLoginScreen() {
        _restartFromLoginEvent.publishEvent()
    }
}