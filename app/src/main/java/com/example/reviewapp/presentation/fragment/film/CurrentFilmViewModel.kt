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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
    private val filmId = navArgs.filmId

    lateinit var rating: Flow<Review?>

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _currentFilm = MutableLiveData<Film>()
    val currentFilm = _currentFilm.share()

    private val _listReviews = MutableLiveData<List<Review>>()
    val listReviews = _listReviews.share()

    private val _listAccount = MutableLiveData<List<Account>>()
    val listAccount = _listAccount.share()

    private val _clearReviewEvent = MutableUnitLiveEvent()
    val clearReviewEvent = _clearReviewEvent.share()

    init {
        viewModelScope.launch {
            _listAccount.value = accountsRepository.getListAccount()

            rating = combine(
                accountsRepository.getAccount(),
                reviewsRepository.getReviewsByFilmId(filmId)
            ) { account, reviews ->
                reviews.firstOrNull { it.accountId == account?.id }
            }

            filmsRepository.getFilmById(filmId).collect {
                _currentFilm.value = it
            }
        }
        viewModelScope.launch {
            reviewsRepository.getReviewsByFilmId(filmId).collect { listReviews ->
                    _listReviews.value = listReviews.filter { it.review != null }
            }
        }
    }

    fun selectRatingFilm(rating: Int) = viewModelScope.launch {
        accountsRepository.getAccount().collect { account ->
            if (account != null) {
                reviewsRepository.selectRatingFilm(account.id, filmId, rating)
                filmsRepository.updateAvg(filmId, reviewsRepository.calculateAverage(filmId))
            }
        }
    }

    fun addReviewForFilm(review: String) = viewModelScope.launch {
        try {
            accountsRepository.getAccount().collect { account ->
                if (account != null) {
                    reviewsRepository.addReviewForFilm(account.id, filmId, review)
                    clearReviewField()
                }
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
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