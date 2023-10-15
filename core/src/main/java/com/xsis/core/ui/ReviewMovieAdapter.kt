package com.xsis.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xsis.core.R
import com.xsis.core.databinding.RvReviewBinding
import com.xsis.core.domain.model.ReviewMovieModel

class ReviewMovieAdapter : RecyclerView.Adapter<ReviewMovieAdapter.ListViewHolder>() {
    private var listData = ArrayList<ReviewMovieModel>()
    var onItemClick: ((ReviewMovieModel) -> Unit)? = null


    fun setData(newListData: List<ReviewMovieModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_review, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvReviewBinding.bind(itemView)
        fun bind(data: ReviewMovieModel) {
            with(binding) {
                tvRating.text = if (data.rating != null) data.rating.toString() else "0.0"
                tvName.text = data.author
                tvContent.text = data.content
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, data.avatarPath))
                    .error(R.drawable.ic_person)
                    .into(avatar)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }
}