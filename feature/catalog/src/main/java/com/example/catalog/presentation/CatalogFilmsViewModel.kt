package com.example.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.entities.Film
import com.example.catalog.domain.usecases.GetListFilmsUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.utils.MutableLiveEvent
import com.example.presentation.utils.publishEvent
import com.example.presentation.utils.share
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