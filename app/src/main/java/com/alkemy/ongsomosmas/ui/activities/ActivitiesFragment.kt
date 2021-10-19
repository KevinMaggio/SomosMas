package com.alkemy.ongsomosmas.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.databinding.FragmentActivitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding
    private val activitiesViewModel: ActivitiesViewModel by viewModels()
    private lateinit var activitiesAdapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)

        activitiesViewModel.activitiesResult.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.loading.root.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.loading.root.isVisible = false
                    activitiesAdapter = it.data?.let { it1 -> ActivitiesAdapter(it1) }!!
                    // add divider to recycler view
                    val itemDecoration =
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    ResourcesCompat.getDrawable(resources, R.drawable.separator_layer, null)
                        ?.let { it1 ->
                            itemDecoration.setDrawable(
                                it1
                            )
                        }
                    binding.rvActivities.addItemDecoration(itemDecoration)
                    binding.rvActivities.adapter = activitiesAdapter

                }
                Resource.Status.ERROR -> {
                    binding.loading.root.isVisible = false
                    binding.activitiesError.root.isVisible = true
                    binding.activitiesError.btnRetry.setOnClickListener {
                        activitiesViewModel.getActivitiesResult()
                        binding.activitiesError.root.isVisible = false
                    }

                }
            }
        })

        return binding.root
    }
}