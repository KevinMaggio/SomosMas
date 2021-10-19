package com.alkemy.ongsomosmas.ui.news

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.getTimeFormatted
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.data.parseToDate
import com.alkemy.ongsomosmas.databinding.NewsCardsBinding
import com.squareup.picasso.Picasso

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = NewsCardsBinding.bind(view)
    val newsName: TextView = binding.tvNewsName
    val newsImage: ImageView = binding.ivNewsFragment
    val newsDescription: TextView = binding.tvNewsDescription
    val newsDate: TextView = binding.tvNewsDate


    fun bind(newsList: NewsResponse) {
        Picasso.get()
            .load(newsList.image)
            .into(newsImage)

        newsName.text = newsList.name
        newsDescription.text =
            HtmlCompat.fromHtml(newsList.content ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)

        val date = newsList.createdAt?.parseToDate()
        val result = date?.getTimeFormatted("dd/MM/YYYY")
        val publication = binding.root.context.getString(R.string.activities_date)
        newsDate.text = "$publication: $result"
    }

}