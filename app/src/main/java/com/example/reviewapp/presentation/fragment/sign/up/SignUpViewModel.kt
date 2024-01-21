package com.example.reviewapp.presentation.fragment.sign.up

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.R
import com.example.reviewapp.model.AccountAlreadyExistsException
import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.PasswordMismatchException
import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.entities.SignUpData
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.utils.MutableUnitLiveEvent
import com.example.reviewapp.utils.publishEvent
import com.example.reviewapp.utils.requireValue
import com.example.reviewapp.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showSuccessSignUpMessageEvent = MutableUnitLiveEvent()
    val showSuccessSignUpMessageEvent = _showSuccessSignUpMessageEvent.share()

    fun signUp(signUpData: SignUpData) = viewModelScope.launch {
        showProgress()
        try {
            accountsRepository.signUp(signUpData)
            showSuccessMessage()
            goBack()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: PasswordMismatchException) {
            processPasswordMismatchException()
        } catch (e: AccountAlreadyExistsException) {
            processAccountAlreadyExistsException()
        } finally {
            hideProgress()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException){
        _state.value = when(e.field){
            Field.Email -> _state.requireValue().copy(emailErrorMessageRes = R.string.field_is_empty)
            Field.Password -> _state.requireValue().copy(passwordErrorMessageRes = R.string.field_is_empty)
            Field.Username -> _state.requireValue().copy(usernameErrorMessageRes = R.string.field_is_empty)
            else -> throw IllegalStateException("Unknown field")
        }
    }

    private fun processPasswordMismatchException(){
        _state.value =_state.requireValue().copy(repeatPasswordErrorMessageRes = R.string.password_missmatch)
    }

    private fun processAccountAlreadyExistsException(){
        _state.value = _state.requireValue().copy(emailErrorMessageRes = R.string.account_already_exists)
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }
    private fun hideProgress(){
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    private fun showSuccessMessage() = _showSuccessSignUpMessageEvent.publishEvent()

    private fun goBack() = _goBackEvent.publishEvent()

    data class State(
        @StringRes val emailErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val passwordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val repeatPasswordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val usernameErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }

    companion object {
        const val NO_ERROR_MESSAGE = 0
    }

}