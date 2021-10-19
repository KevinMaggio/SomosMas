package com.alkemy.ongsomosmas.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.model.WelcomeResponse
import com.alkemy.ongsomosmas.data.welcome.WelcomeHolder

class WelcomeAdapter(private var welcomeList: List<WelcomeResponse>) :
    RecyclerView.Adapter<WelcomeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.welcome_slides, parent, false)
        return WelcomeHolder(view)
    }

    override fun onBindViewHolder(holder: WelcomeHolder, position: Int) {
        val slides = welcomeList[position]
        holder.bind(slides)
    }

    override fun getItemCount(): Int = welcomeList.size

}