package com.example.reviewapp.presentation.fragment.film

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.reviewapp.R
import com.example.reviewapp.databinding.FragmentCurrentFilmBinding
import com.example.reviewapp.model.films.entities.Film
import com.example.reviewapp.presentation.base.BaseFragment
import com.example.reviewapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentFilmFragment : BaseFragment<FragmentCurrentFilmBinding>(
    R.layout.fragment_current_film,
    FragmentCurrentFilmBinding::inflate
) {

    override val viewModel by viewModels<CurrentFilmViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = setupReview()

        binding.addReview.setOnClickListener {
            addReviewForFilm()
        }
        enterOnBoard(binding.reviewEditText)

        viewModel.currentFilm.observe(viewLifecycleOwner) { renderFilm(it) }

        viewModel.listAccount.observe(viewLifecycleOwner) { adapter.setAccounts(it) }

        viewModel.listReviews.observe(viewLifecycleOwner) { adapter.renderReviews(it) }

        observeState()
        selectRatingFilm()
        observeClearReviewEvent()
    }

    private fun setupReview(): CurrentFilmAdapter {
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CurrentFilmAdapter()
        binding.reviewRecyclerView.adapter = adapter
        return adapter
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.reviewTextInput.error =
            if (it.emptyReviewError) getString(R.string.field_is_empty) else null

    }

    private fun selectRatingFilm() {
        binding.ratingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selectRating = binding.ratingSpinner.selectedItem.toString().toInt()
                if (selectRating != 0) {
                    viewModel.selectRatingFilm(selectRating)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun addReviewForFilm() {
        viewModel.addReviewForFilm(
            review = binding.reviewEditText.text.toString()
        )
        binding.reviewEditText.clearFocus()
    }

    private fun observeClearReviewEvent() =
        viewModel.clearReviewEvent.observe(viewLifecycleOwner) {
            binding.reviewEditText.text?.clear()
            hideKeyboard()
        }

    private fun enterOnBoard(reviewEditText: EditText) {
        reviewEditText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    addReviewForFilm()
                    true
                }

                else -> false
            }
        }
    }

    private fun renderFilm(film: Film) {
        binding.titleFilm.text = film.title
        binding.descriptionFilm.text = film.description
        binding.ratingFilm.text = film.summaryScore.toString()
        viewModel.rating.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.ratingSpinner.setSelection(it)
            }
        }
        if (film.img.isNotBlank()) {
            Glide.with(binding.filmAvatar.context)
                .load(film.img)
                .placeholder(R.drawable.ic_not_found_avatar_film)
                .error(R.drawable.ic_not_found_avatar_film)
                .into(binding.filmAvatar)
        } else {
            binding.filmAvatar.setImageResource(R.drawable.ic_not_found_avatar_film)
        }
    }

}
