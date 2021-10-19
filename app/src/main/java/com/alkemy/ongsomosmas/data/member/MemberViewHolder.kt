package com.alkemy.ongsomosmas.data.member

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.data.model.MemberResponse
import com.alkemy.ongsomosmas.databinding.MemberItemBinding
import com.squareup.picasso.Picasso

class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = MemberItemBinding.bind(view)

    val itemImage: ImageView = binding.ivMember

    fun bind(members: MemberResponse) {
        Picasso.get().load(members.image).into(itemImage)
        binding.tvMemberName.text = members.name
        binding.tvMemberDescription.text = members.description.removePrefix("<p>").removeSuffix("</p>")
    }
}