package com.alkemy.ongsomosmas.ui.aboutus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.member.MemberViewHolder
import com.alkemy.ongsomosmas.data.model.MemberResponse

class MemberAdapter(
    private var members: List<MemberResponse>,
    private val onClick: (memberClicked: MemberResponse) -> Unit
) :
    RecyclerView.Adapter<MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(members[position])

        holder.itemImage.setOnClickListener {
            onClick.invoke(members[position])
        }
    }

    override fun getItemCount() = members.size


}