package com.alkemy.ongsomosmas.data.welcome

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.data.model.WelcomeResponse
import com.alkemy.ongsomosmas.databinding.WelcomeSlidesBinding
import com.squareup.picasso.Picasso

class WelcomeHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = WelcomeSlidesBinding.bind(view)
    val itemImage: ImageView = binding.ivSlides
    val itemTitle: TextView = binding.tvSlidesName
    val itemDescription: TextView = binding.tvSlidesDescription


    fun bind(welcomeList: WelcomeResponse) {
        Picasso.get()
            .load(welcomeList.image)
            .into(itemImage)

        itemTitle.text = welcomeList.name
        itemDescription.text =
            HtmlCompat.fromHtml(welcomeList.content ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}