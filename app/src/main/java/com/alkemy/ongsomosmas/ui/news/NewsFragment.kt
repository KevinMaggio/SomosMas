package com.alkemy.ongsomosmas.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapterFragment: NewsAdapterFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        newsViewModel.newsFragmentResult.observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        binding.loading.root.isVisible = true
                    }
                    Resource.Status.SUCCESS -> {
                        binding.loading.root.isVisible = false
                        newsAdapterFragment = it.data?.let { it1 -> NewsAdapterFragment(it1) }!!
                        binding.rvNewsFragment.adapter = newsAdapterFragment
                    }
                    Resource.Status.ERROR -> {
                        binding.loading.root.isVisible = false
                        binding.newsError.root.isVisible = true
                        binding.newsError.btnRetry.setOnClickListener {
                            newsViewModel.getNewsFragmentResponse()
                            binding.newsError.root.isVisible = false
                        }
                    }
                }
            })
        return binding.root
    }

}