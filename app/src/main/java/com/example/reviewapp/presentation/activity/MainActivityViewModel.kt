package com.example.reviewapp.presentation.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.accountfeature.domain.usecases.GetAccountUseCase
import com.example.reviewapp.accountfeature.domain.usecases.IsSignedInUseCase
import com.example.reviewapp.accountfeature.domain.usecases.LogoutUseCase
import com.example.reviewapp.core.utils.MutableLiveEvent
import com.example.reviewapp.core.utils.publishEvent
import com.example.reviewapp.core.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isSignedInUseCase: IsSignedInUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    private val _restartFromLoginEvent = MutableLiveEvent<Unit>()
    val restartWithSignInEvent = _restartFromLoginEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(isSignedInUseCase.isSignedIn())
            getAccountUseCase.getAccount().collect {
                if (it == null) {
                    _username.value = ""
                } else {
                    _username.value = "@${it.username}"
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.logout()
        restartAppFromLoginScreen()
    }
    private fun restartAppFromLoginScreen() {
        _restartFromLoginEvent.publishEvent()
    }
}