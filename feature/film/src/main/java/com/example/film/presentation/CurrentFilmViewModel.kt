package com.example.film.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.film.domain.entities.Account
import com.example.film.domain.entities.Film
import com.example.film.domain.entities.Review
import com.example.film.domain.usecases.AddReviewForFilmUseCase
import com.example.film.domain.usecases.CalculateAverageUseCase
import com.example.film.domain.usecases.GetAccountUseCase
import com.example.film.domain.usecases.GetFilmByIdUseCase
import com.example.film.domain.usecases.GetListAccountUseCase
import com.example.film.domain.usecases.GetReviewsByFilmIdUseCase
import com.example.film.domain.usecases.SelectRatingFilmUseCase
import com.example.film.domain.usecases.UpdateAvgUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.constants.Field
import com.example.presentation.exceptions.EmptyFieldException
import com.example.presentation.exceptions.requireValue
import com.example.presentation.utils.MutableUnitLiveEvent
import com.example.presentation.utils.publishEvent
import com.example.presentation.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentFilmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAccountUseCase: GetAccountUseCase,
    private val getListAccountUseCase: GetListAccountUseCase,
    private val getReviewsByFilmIdUseCase: GetReviewsByFilmIdUseCase,
    private val selectRatingFilmUseCase: SelectRatingFilmUseCase,
    private val calculateAverageUseCase: CalculateAverageUseCase,
    private val addReviewForFilmUseCase: AddReviewForFilmUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val updateAvgUseCase: UpdateAvgUseCase
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
            _listAccount.value = getListAccountUseCase.getListAccount()
            getFilmByIdUseCase.getFilmById(filmId).collect {
                _currentFilm.value = it
            }
        }
        viewModelScope.launch {

            rating = combine(
                getAccountUseCase.getAccountId(),
                getReviewsByFilmIdUseCase.getReviewsByFilmId(filmId)
            ) { accountId, reviews ->
                reviews.firstOrNull { it.accountId == accountId }
            }
        }
        viewModelScope.launch {
            getReviewsByFilmIdUseCase.getReviewsByFilmId(filmId).collect { listReviews ->
                    _listReviews.value = listReviews.filter { it.review != null }
            }
        }
    }

    fun selectRatingFilm(rating: Int) = viewModelScope.launch {
        getAccountUseCase.getAccountId().collect { accountId ->
            if (accountId != null) {
                selectRatingFilmUseCase.selectRatingFilm(accountId, filmId, rating)
                updateAvgUseCase.updateAvg(filmId, calculateAverageUseCase.calculateAverage(filmId))
            }
        }
    }

    fun addReviewForFilm(review: String) = viewModelScope.launch {
        try {
            getAccountUseCase.getAccountId().collect { accountId ->
                if (accountId != null) {
                    addReviewForFilmUseCase.addReviewForFilm(accountId, filmId, review)
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