package com.xsis.core.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.xsis.core.R
import com.xsis.core.databinding.RvMovie2Binding
import com.xsis.core.domain.model.MovieModel
import java.util.Locale

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ListViewHolder>(), Filterable {
    private var listData = ArrayList<MovieModel>()
    private var listFiltered = ArrayList<MovieModel>()
    var onItemClick: ((MovieModel) -> Unit)? = null


    fun setData(newListData: List<MovieModel>?) {
        if (newListData == null) return
        listData.clear()
        listFiltered.clear()
        listData.addAll(newListData)
        listFiltered.addAll(listData)
        notifyDataSetChanged()
    }

    fun addItem(item: MovieModel) {
        val position = listData.size
        listData.add(position, item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_movie2, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvMovie2Binding.bind(itemView)
        fun bind(data: MovieModel) {
            with(binding) {
                tvTitle.text = data.title
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, data.poster_path))
                    .error(R.drawable.ic_movie)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                    })
                    .into(poster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return contactFilter
    }

    private val contactFilter: Filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<MovieModel> = java.util.ArrayList()
            if (constraint.isEmpty()) {
                filteredList.clear()
                filteredList.addAll(listFiltered)
            } else {
                filteredList.clear()
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in listFiltered) {
                    if (item.title?.lowercase()?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listData.clear()
            listData.addAll(results.values as java.util.ArrayList<MovieModel>)
            notifyDataSetChanged()
        }
    }
}