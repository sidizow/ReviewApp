package com.example.reviewapp.catalogfilmsfeature.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.catalogfilmsfeature.domain.entities.Film
import com.example.reviewapp.catalogfilmsfeature.domain.usecases.GetListFilmsUseCase
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.core.utils.MutableLiveEvent
import com.example.reviewapp.core.utils.publishEvent
import com.example.reviewapp.core.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogFilmsViewModel @Inject constructor(
    private val getListFilmsUseCase: GetListFilmsUseCase
) : BaseViewModel(), CatalogAdapter.ActionListener {

    private val _listFilms = MutableLiveData<List<Film>>()
    val listFilms = _listFilms.share()

    private val _openFilmPageEvent = MutableLiveEvent<Long>()
    val openFilmPageEvent = _openFilmPageEvent.share()

    init {
        viewModelScope.launch {
            getListFilmsUseCase.getListFilms().collect{
                _listFilms.value = it
            }
        }
    }

    override fun onOpenFilmPage(filmId: Long) {
        _openFilmPageEvent.publishEvent(filmId)
    }

}