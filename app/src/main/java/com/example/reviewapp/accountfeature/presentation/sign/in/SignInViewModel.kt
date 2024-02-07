package com.example.reviewapp.accountfeature.presentation.sign.`in`

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.accountfeature.domain.usecases.SignInUseCase
import com.example.reviewapp.core.exceptions.AuthException
import com.example.reviewapp.core.exceptions.EmptyFieldException
import com.example.reviewapp.core.constants.Field
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.core.utils.MutableUnitLiveEvent
import com.example.reviewapp.core.utils.publishEvent
import com.example.reviewapp.core.exceptions.requireValue
import com.example.reviewapp.core.utils.share
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