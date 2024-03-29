package com.example.catalog.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catalog.R
import com.example.catalog.databinding.ItemFilmBinding
import com.example.catalog.domain.entities.Film

class CatalogAdapter(
    private val actionListener: ActionListener,
) : RecyclerView.Adapter<CatalogAdapter.FilmViewHolder>(), View.OnClickListener {

    private var films: List<Film> = emptyList()

    class FilmViewHolder(val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val film = v.tag as Film
        val filmId = film.id
        actionListener.onOpenFilmPage(filmId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return FilmViewHolder(binding)
    }


    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        with(holder.binding) {
            holder.itemView.tag = film
            titleFilm.text = film.title
            descriptionFilm.text = film.description
            ratingFilm.text = film.summaryScore.toString()
            if (film.img.isNotBlank()) {
                Glide.with(filmAvatar.context)
                    .load(film.img)
                    .placeholder(R.drawable.ic_not_found_avatar_film)
                    .error(R.drawable.ic_not_found_avatar_film)
                    .into(filmAvatar)
            } else {
                filmAvatar.setImageResource(R.drawable.ic_not_found_avatar_film)
            }
        }
    }

    fun renderFilms(films: List<Film>) {
        val diffResult = DiffUtil.calculateDiff(CatalogFilmsDiffCallback(this.films, films))
        this.films = films
        diffResult.dispatchUpdatesTo(this)
    }

    interface ActionListener {
        fun onOpenFilmPage(filmId: Long)
    }

}