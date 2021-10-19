package com.alkemy.ongsomosmas.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.getTimeFormatted
import com.alkemy.ongsomosmas.data.model.ActivitiesResponse
import com.alkemy.ongsomosmas.data.parseToDate
import com.alkemy.ongsomosmas.databinding.ActivitiesItemsBinding
import com.squareup.picasso.Picasso

class ActivitiesAdapter(private var activitiesList: List<ActivitiesResponse>) :
    RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ActivitiesItemsBinding.bind(view)
        val itemTitle: TextView = binding.tvTitle
        val itemDescription: TextView = binding.tvActivitiesDescription
        val itemDate: TextView = binding.tvPublishingDate
        val itemImage: ImageView = binding.ivActivities

        fun bind(activities: ActivitiesResponse) {
            Picasso.get().load(activities.image).into(itemImage)
            itemTitle.text = activities.name
            itemDescription.text =
                HtmlCompat.fromHtml(activities.description?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString()
            val date = activities.createdAt.parseToDate()
            val publication = binding.root.context.getString(R.string.activities_date)
            val result = "$publication: " + date?.getTimeFormatted("dd/MM/YYYY")
            itemDate.text = result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activities_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activitiesList[position])
    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

}