package com.example.reviewapp.presentation.fragment.film

import androidx.fragment.app.viewModels
import com.example.reviewapp.presentation.base.BaseFragment

class CurrentFilmFragment: BaseFragment() {

    override val viewModel by viewModels<CurrentFilmViewModel>()

}