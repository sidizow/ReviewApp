package com.example.reviewapp.filmfeature.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.accountfeature.domain.usecases.GetAccountUseCase
import com.example.reviewapp.accountfeature.domain.usecases.GetListAccountUseCase
import com.example.reviewapp.catalogfilmsfeature.domain.entities.Film
import com.example.reviewapp.catalogfilmsfeature.domain.usecases.GetFilmByIdUseCase
import com.example.reviewapp.catalogfilmsfeature.domain.usecases.UpdateAvgUseCase
import com.example.reviewapp.filmfeature.domain.entities.Review
import com.example.reviewapp.filmfeature.domain.usecases.AddReviewForFilmUseCase
import com.example.reviewapp.filmfeature.domain.usecases.CalculateAverageUseCase
import com.example.reviewapp.filmfeature.domain.usecases.GetReviewsByFilmIdUseCase
import com.example.reviewapp.filmfeature.domain.usecases.SelectRatingFilmUseCase
import com.example.reviewapp.core.exceptions.EmptyFieldException
import com.example.reviewapp.core.constants.Field
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.core.utils.MutableUnitLiveEvent
import com.example.reviewapp.core.utils.publishEvent
import com.example.reviewapp.core.exceptions.requireValue
import com.example.reviewapp.core.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentFilmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getListAccountUseCase: GetListAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
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
                getAccountUseCase.getAccount(),
                getReviewsByFilmIdUseCase.getReviewsByFilmId(filmId)
            ) { account, reviews ->
                reviews.firstOrNull { it.accountId == account?.id }
            }
        }
        viewModelScope.launch {
            getReviewsByFilmIdUseCase.getReviewsByFilmId(filmId).collect { listReviews ->
                    _listReviews.value = listReviews.filter { it.review != null }
            }
        }
    }

    fun selectRatingFilm(rating: Int) = viewModelScope.launch {
        getAccountUseCase.getAccount().collect { account ->
            if (account != null) {
                selectRatingFilmUseCase.selectRatingFilm(account.id, filmId, rating)
                updateAvgUseCase.updateAvg(filmId, calculateAverageUseCase.calculateAverage(filmId))
            }
        }
    }

    fun addReviewForFilm(review: String) = viewModelScope.launch {
        try {
            getAccountUseCase.getAccount().collect { account ->
                if (account != null) {
                    addReviewForFilmUseCase.addReviewForFilm(account.id, filmId, review)
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