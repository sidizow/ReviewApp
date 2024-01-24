package com.example.reviewapp.presentation.fragment.film

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.model.EmptyFieldException
import com.example.reviewapp.model.Field
import com.example.reviewapp.model.accounts.AccountsRepository
import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.model.reviews.ReviewsRepository
import com.example.reviewapp.model.reviews.entities.Review
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.utils.MutableUnitLiveEvent
import com.example.reviewapp.utils.publishEvent
import com.example.reviewapp.utils.requireValue
import com.example.reviewapp.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentFilmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountsRepository: AccountsRepository,
    private val reviewsRepository: ReviewsRepository,
    private val filmsRepository: FilmsRepository,
) : BaseViewModel() {

    private val navArgs = CurrentFilmFragmentArgs
        .fromSavedStateHandle(savedStateHandle)
    private val idFilm = navArgs.idFilm

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _currentFilm = MutableLiveData<Film>()
    val currentFilm = _currentFilm.share()

    private val _listReviews = MutableLiveData<List<Review>>()
    val listReviews = _listReviews.share()

    private val _listAccount = MutableLiveData<List<Account>>()
    val listAccount = _listAccount.share()

    private val _rating = MutableLiveData<Int>()
    val rating = _rating.share()

    private val _clearReviewEvent = MutableUnitLiveEvent()
    val clearReviewEvent = _clearReviewEvent.share()

    init {
        viewModelScope.launch {
            _currentFilm.value = filmsRepository.getFilmById(idFilm)
            getAccountAndReviews()
            accountsRepository.getAccount().collect {
                if (it != null) {
                    _rating.value = reviewsRepository.getRatingByIdFilmAndIdAccount(idFilm, it.id)
                }
            }
        }
    }

    private fun getAccountAndReviews() = viewModelScope.launch {
        _listAccount.value = accountsRepository.getListAccount()
        _listReviews.value = reviewsRepository.getReviewByIdFilm(idFilm)
    }

    fun selectRatingFilm(rating: Int) = viewModelScope.launch {
        accountsRepository.getAccount().collect {
            if (it != null) {
                reviewsRepository.selectRatingFilm(it.id, idFilm, rating)
                getAccountAndReviews()
            }
        }
    }

    fun addReviewForFilm(review: String) = viewModelScope.launch {
        accountsRepository.getAccount().collect {
            if (it != null) {
                try {
                    reviewsRepository.addReviewForFilm(it.id, idFilm, review)
                    clearReviewField()
                    getAccountAndReviews()
                }catch (e: EmptyFieldException){
                    processEmptyFieldException(e)
                }
            }
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyReviewError = e.field == Field.Review
        )
    }


    private fun clearReviewField() = _clearReviewEvent.publishEvent()

    data class State(
        val emptyReviewError: Boolean = false
    )
}