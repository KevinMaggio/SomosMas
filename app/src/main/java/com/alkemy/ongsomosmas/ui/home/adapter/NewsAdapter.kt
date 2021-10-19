package com.alkemy.ongsomosmas.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.databinding.NewsItemsBinding
import com.squareup.picasso.Picasso

class NewsAdapter(
    private var news: List<NewsResponse>,
    private val onClickLastItem: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NewsItemsBinding.bind(view)
        val itemTitle: TextView = binding.tvTitle
        val itemImage: ImageView = binding.ivNews

        fun bind(news: NewsResponse) {
            Picasso.get().load(news.image).into(itemImage)
            itemTitle.text = news.name
        }
    }

    class LastViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_last_item, parent, false)
                view.setOnClickListener { onClickLastItem() }
                LastViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_items, parent, false)
                ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < 4) {
            (holder as ViewHolder).bind(news[position])
        }
    }

    override fun getItemCount(): Int {
        val limit = 5
        return if (news.size > limit) {
            limit
        } else {
            news.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        val hasMore = news.size > 4
        return if (hasMore && position == 4) 1 else 2
    }

}