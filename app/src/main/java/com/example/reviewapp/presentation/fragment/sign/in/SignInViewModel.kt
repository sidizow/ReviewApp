package com.example.reviewapp.presentation.fragment.sign.`in`

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.model.AuthException
import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.utils.MutableUnitLiveEvent
import com.example.reviewapp.utils.publishEvent
import com.example.reviewapp.utils.requireValue
import com.example.reviewapp.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToast = MutableUnitLiveEvent()
    val showAuthErrorToast = _showAuthErrorToast.share()

    private val _navigateToCatalogFilmsEvent = MutableUnitLiveEvent()
    val navigateToCatalogFilmsEvent = _navigateToCatalogFilmsEvent.share()

    fun singIn(email: String, password: String) = viewModelScope.launch{
        showProgress()
        try {
            accountsRepository.signIn(email,password)
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