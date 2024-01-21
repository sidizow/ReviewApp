package com.example.reviewapp.presentation.fragment.film

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reviewapp.R
import com.example.reviewapp.databinding.ItemReviewBinding
import com.example.reviewapp.model.accounts.entities.Account
import com.example.reviewapp.model.reviews.entities.Review

class CurrentFilmAdapter : RecyclerView.Adapter<CurrentFilmAdapter.ReviewViewHolder>() {

    private var reviews: List<Review> = emptyList()
    private var accounts: List<Account> = emptyList()

    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        with(holder.binding) {
            if (!review.review.isNullOrBlank()) {
                username.text = getUsernameById(review.idAccount)
                reviewerRating.text = reviewerRating.context.getString(
                    R.string.reviewer_rating,
                    review.rating?.let { it.toString()} ?: "Нет оценки"
                )
                reviewerReview.text = review.review
            }
        }
    }

    override fun getItemCount(): Int = reviews.size

    fun setAccounts(accounts: List<Account>){
        this.accounts = accounts
    }

    fun renderReviews(reviews: List<Review>) {
        Log.d("TAG", "old list: " + this.reviews.toString() + "new list: " + reviews.toString())
        val diffResult = DiffUtil.calculateDiff(CurrentFilmDiffCallback(this.reviews, reviews))
        this.reviews = reviews
        diffResult.dispatchUpdatesTo(this)
    }

    private fun getUsernameById(id: Long): String = accounts.first { it.id == id }.username


}