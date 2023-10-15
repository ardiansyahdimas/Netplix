package com.xsis.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.xsis.core.R
import com.xsis.core.databinding.RvCarouselBinding
import com.xsis.core.domain.model.CarouselModel

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.ListViewHolder>() {
    private var listData = ArrayList<CarouselModel>()
    var onItemClick: ((CarouselModel) -> Unit)? = null


    fun setData(newListData: List<CarouselModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_carousel, parent, false))

    override fun getItemCount() = if (listData.size > 10) 10 else listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvCarouselBinding.bind(itemView)
        fun bind(data: CarouselModel) {
            with(binding) {
                tvTitle.text = data.title
                tvDesc.text = data.overview.toString()
                tvDate.text = data.release_date.toString()
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
}