package com.example.signin.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.presentation.base.BaseViewModel
import com.example.presentation.constants.Field
import com.example.presentation.exceptions.AuthException
import com.example.presentation.exceptions.EmptyFieldException
import com.example.presentation.exceptions.requireValue
import com.example.presentation.utils.MutableUnitLiveEvent
import com.example.presentation.utils.publishEvent
import com.example.presentation.utils.share
import com.example.signin.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signedInUseCase: SignInUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToast = MutableUnitLiveEvent()
    val showAuthErrorToast = _showAuthErrorToast.share()

    private val _navigateToCatalogFilmsEvent = MutableUnitLiveEvent()
    val navigateToCatalogFilmsEvent = _navigateToCatalogFilmsEvent.share()

    fun singIn(email: String, password: CharArray) = viewModelScope.launch{
        showProgress()
        try {
            signedInUseCase.signIn(email,password)
            launchCatalogFilmsScreen()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: AuthException){
            processAuthException()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
            signInInProgress = false
        )
    }

    private fun processAuthException() {
        _state.value = _state.requireValue().copy(
            signInInProgress = false
        )
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun clearPasswordField() =_clearPasswordEvent.publishEvent()

    private fun showAuthErrorToast() = _showAuthErrorToast.publishEvent()

    private fun launchCatalogFilmsScreen() = _navigateToCatalogFilmsEvent.publishEvent()

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

}