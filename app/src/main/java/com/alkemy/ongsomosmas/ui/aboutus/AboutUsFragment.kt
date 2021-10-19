package com.alkemy.ongsomosmas.ui.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.MemberResponse
import com.alkemy.ongsomosmas.databinding.FragmentAboutUsBinding
import com.alkemy.ongsomosmas.utils.EventConstants
import com.alkemy.ongsomosmas.utils.sendLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private val aboutUsViewModel by viewModels<AboutUsViewModel>()
    private lateinit var memberAdapter: MemberAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        aboutUsViewModel.memberResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.loading.root.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    sendLog(EventConstants.MEMBER_SUCCESS, "Member list retrieved successfully")
                    binding.loading.root.isVisible = false
                    memberAdapter = it.data.let { members ->
                        members?.let {
                            MemberAdapter(
                                members,
                                onClick = { members ->
                                    sendLog(
                                        EventConstants.MEMBER_PRESSED,
                                        "User pressed on a member"
                                    )
                                    navigateToMember(members)
                                })
                        }!!
                    }
                    binding.rvMembers.adapter = memberAdapter
                }
                Resource.Status.ERROR -> {
                    binding.loading.root.isVisible = !it.isError()
                    sendLog(EventConstants.MEMBER_ERROR, "Member list retrieved failed")

                    binding.error.apply {
                        root.isVisible = it.isError() || it.data?.isEmpty() ?: true
                        btnRetry.setOnClickListener {
                            aboutUsViewModel.getMemberResponse()
                            binding.error.root.isVisible = false
                        }
                    }
                }
            }
        })
        return binding.root
    }

    private fun navigateToMember(memberClicked: MemberResponse) {
        val data = Bundle()
        data.putSerializable(MemberDetailFragment.EXTRA_MEMBER, memberClicked)
        findNavController().navigate(R.id.nav_fragment_member_detail, data)
    }
}
