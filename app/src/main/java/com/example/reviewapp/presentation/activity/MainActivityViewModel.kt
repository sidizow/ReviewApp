package com.example.reviewapp.presentation.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.utils.publishEvent
import com.example.presentation.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isSignedInUseCase: com.example.signin.domain.usecases.IsSignedInUseCase,
    private val getAccountUseCase: com.example.signin.domain.usecases.GetAccountUseCase,
    private val logoutUseCase: com.example.signin.domain.usecases.LogoutUseCase
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    private val _launchMainScreenEvent = com.example.presentation.utils.MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    private val _restartFromLoginEvent = com.example.presentation.utils.MutableLiveEvent<Unit>()
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