package com.example.reviewapp.filmfeature.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reviewapp.R
import com.example.reviewapp.databinding.ItemReviewBinding
import com.example.reviewapp.accountfeature.domain.entities.Account
import com.example.reviewapp.filmfeature.domain.entities.Review

class CurrentFilmAdapter : RecyclerView.Adapter<CurrentFilmAdapter.ReviewViewHolder>() {

    private var currentReviews: List<Review> = emptyList()
    private var accounts: List<Account> = emptyList()

    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = currentReviews[position]
        with(holder.binding) {
            username.text = getUsernameById(review.accountId)
            reviewerRating.text = reviewerRating.context.getString(
                R.string.reviewer_rating,
                review.rating?.toString() ?: "Нет оценки"
            )
            reviewerReview.text = review.review

        }
    }

    override fun getItemCount(): Int = currentReviews.size

    fun setAccounts(accounts: List<Account>) {
            this.accounts = accounts
    }

    fun renderReviews(reviews: List<Review>) {
            val diffResult =
                DiffUtil.calculateDiff(CurrentFilmDiffCallback(this.currentReviews, reviews))
            this.currentReviews = reviews
            diffResult.dispatchUpdatesTo(this)
    }

    private fun getUsernameById(id: Long): String = accounts.first { it.id == id }.username


}