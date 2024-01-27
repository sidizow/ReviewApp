package com.example.reviewapp.presentation.fragment.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.model.films.FilmsRepository
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.presentation.base.BaseViewModel
import com.example.reviewapp.utils.MutableLiveEvent
import com.example.reviewapp.utils.publishEvent
import com.example.reviewapp.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogFilmsViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository,
) : BaseViewModel(), CatalogAdapter.ActionListener {

    private val _listFilms = MutableLiveData<List<Film>>()
    val listFilms = _listFilms.share()

    private val _openFilmPageEvent = MutableLiveEvent<Long>()
    val openFilmPageEvent = _openFilmPageEvent.share()

    init {
        viewModelScope.launch {
            filmsRepository.getFlowFilms().collect{
                _listFilms.value = it
            }
        }
    }

    override fun onOpenFilmPage(filmId: Long) {
        _openFilmPageEvent.publishEvent(filmId)
    }

}