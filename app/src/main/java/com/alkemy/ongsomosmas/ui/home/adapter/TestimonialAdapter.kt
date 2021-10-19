package com.alkemy.ongsomosmas.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.model.TestimonialResponse


class TestimonialAdapter(private val listTestimonials: List<TestimonialResponse>,
                         private val onClickLastItem: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun setOnClickListener(onClickListener: OnClickListener) {
        listener = onClickListener
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
                    .inflate(R.layout.item_testimonials, parent, false)
                TestimonialsViewHolder(view, listener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < 4) {
            (holder as TestimonialsViewHolder).render(listTestimonials[position])
        }
    }

    override fun getItemCount(): Int {
        val limit = 5
        return if (listTestimonials.size > limit) {
            limit
        } else {
            listTestimonials.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        val hasMore = listTestimonials.size > 4
        return if (hasMore && position == 4) 1 else 2
    }

    private lateinit var listener: OnClickListener

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

}
