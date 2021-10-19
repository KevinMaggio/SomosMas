package com.alkemy.ongsomosmas.ui.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.alkemy.ongsomosmas.data.getTimeFormatted
import com.alkemy.ongsomosmas.data.model.MemberResponse
import com.alkemy.ongsomosmas.data.parseToDate
import com.alkemy.ongsomosmas.databinding.FragmentMemberDetailBinding
import com.squareup.picasso.Picasso

class MemberDetailFragment : Fragment() {
    companion object {
        const val EXTRA_MEMBER = ""
    }

    private lateinit var binding: FragmentMemberDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMemberDetailBinding.inflate(inflater, container, false)

        //get data from about us fragment
        if (arguments != null) {
            val data = requireArguments().getSerializable(EXTRA_MEMBER) as MemberResponse

            //bind data
            binding.tvName.text = data.name
            binding.tvJob.text =
                HtmlCompat.fromHtml(data.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString()
            val date = data.createdAt.parseToDate()
            binding.tvDate.text = date?.getTimeFormatted("dd/MM/YYYY")
            Picasso.get().load(data.image).into(binding.ivMember)
            Picasso.get().load(data.facebookUrl).into(binding.ivFacebookIcon)
            Picasso.get().load(data.linkedinUrl).into(binding.ivLinkedinIcon)
            binding.ivFacebookIcon.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${data.facebookUrl}"))
                    startActivity(webIntent)
            }
            binding.ivLinkedinIcon.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${data.linkedinUrl}"))
                startActivity(webIntent)
            }
        } else {
            //display error message if arguments are null
            Toast.makeText(context, "Error loading content", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}