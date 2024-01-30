package com.example.reviewapp.presentation.fragment.film

import androidx.recyclerview.widget.DiffUtil
import com.example.reviewapp.model.reviews.entities.Review

class CurrentFilmDiffCallback(
    private val oldList: List<Review>,
    private val newList: List<Review>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].filmId == newList[newItemPosition].filmId &&
                oldList[oldItemPosition].accountId == newList[newItemPosition].accountId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}