package com.example.reviewapp.presentation.fragment.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reviewapp.R
import com.example.reviewapp.databinding.FragmentCatalogFilmsBinding
import com.example.reviewapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFilmsFragment : BaseFragment<FragmentCatalogFilmsBinding>(
    R.layout.fragment_catalog_films,
    FragmentCatalogFilmsBinding::inflate
) {

    override val viewModel by viewModels<CatalogFilmsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = setupCatalog()
        viewModel.listFilms.observe(viewLifecycleOwner) {
            adapter.renderFilms(it)
        }
        observeOpenFilmPageEvent()
    }

    private fun setupCatalog(): CatalogAdapter {
        binding.filmsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CatalogAdapter(viewModel)
        binding.filmsRecyclerView.adapter = adapter
        return adapter
    }

    private fun observeOpenFilmPageEvent() {
        viewModel.openFilmPageEvent.observe(viewLifecycleOwner) {
            val filmIdArgs = it.get()
            if(filmIdArgs != null){
                val direction = CatalogFilmsFragmentDirections.actionCatalogFilmsFragmentToCurrentFilmFragment(filmIdArgs)
                findNavController().navigate(direction)
            }
        }
    }
}
