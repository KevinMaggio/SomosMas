package com.alkemy.ongsomosmas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.databinding.FragmentHomeBinding
import com.alkemy.ongsomosmas.ui.home.adapter.NewsAdapter
import com.alkemy.ongsomosmas.ui.home.adapter.TestimonialAdapter
import com.alkemy.ongsomosmas.ui.home.adapter.WelcomeAdapter
import com.alkemy.ongsomosmas.utils.EventConstants
import com.alkemy.ongsomosmas.utils.sendLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var welcomeAdapter: WelcomeAdapter
    private lateinit var testimonialAdapter: TestimonialAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel.newsResult.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    sendLog(EventConstants.LAST_NEWS_SUCCESS, "Sucessful news get response")
                    newsAdapter = it.data?.let { it1 ->
                        NewsAdapter(
                            it1,
                            onClickLastItem = {
                                navigateToNews()
                                sendLog(
                                    EventConstants.LAST_NEWS_PRESSED_ARROW,
                                    "User pressed see more news' arrow"
                                )
                            })
                    }!!
                    binding.rvNews.adapter = newsAdapter
                }
                Resource.Status.ERROR -> {
                    sendLog(EventConstants.LAST_NEWS_ERROR, "Unsuccessful news get response")
                    binding.newsError.root.isVisible = true
                    binding.newsError.btnRetry.setOnClickListener {
                        homeViewModel.getNewsResponse()
                        binding.newsError.root.isVisible = false
                    }
                }
            }
        })

        //slider
        homeViewModel.welcomeResult.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    sendLog(EventConstants.SLIDER_SUCCESS, "Successful slider get response")
                    welcomeAdapter = it.data?.let { it1 -> WelcomeAdapter(it1) }!!
                    binding.rvSlides.adapter = welcomeAdapter
                }
                Resource.Status.ERROR -> {
                    sendLog(EventConstants.SLIDER_ERROR, "Unsuccessful slider get response")
                    binding.slidesError.root.isVisible = true
                    binding.slidesError.btnRetry.setOnClickListener {
                        homeViewModel.getWelcomeResponse()
                        binding.slidesError.root.isVisible = false
                    }
                }
            }
        })

        homeViewModel.testimonialResult.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    sendLog(
                        EventConstants.TESTIMONIALS_SUCCESS,
                        "Successful testimonials get response"
                    )
                    testimonialAdapter = it.data?.let { it1 ->
                        TestimonialAdapter(
                            it1,
                            onClickLastItem = {
                                sendLog(
                                    EventConstants.LAST_TESTIMONIALS_PRESSED_ARROW,
                                    "User pressed see more testimonials' arrow"
                                )
                            })
                    }!!

                    testimonialAdapter.setOnClickListener(object :
                        TestimonialAdapter.OnClickListener {
                        override fun onItemClick(position: Int) {
                            //TODO en esta linea hacer el pase al fragment DetailsTestimonials
                        }
                    }
                    )
                    binding.rvTestimonial.adapter = testimonialAdapter
                }
                Resource.Status.ERROR -> {
                    sendLog(
                        EventConstants.TESTIMONIALS_ERROR,
                        "Unsuccessful testimonials get response"
                    )
                    binding.errorTestimonials.root.isVisible = true
                    binding.errorTestimonials.btnRetry.setOnClickListener {
                        homeViewModel.getTestimonialResponse()
                        binding.errorTestimonials.root.isVisible = false
                    }
                }
            }
        })


        homeViewModel.isError.observe(viewLifecycleOwner, {
            binding.errorGeneral.root.isVisible = it
            if (it) {
                binding.errorGeneral.btReintentar.apply {
                    setOnClickListener {
                        homeViewModel.apply {
                            getWelcomeResponse()
                            getNewsResponse()
                            getTestimonialResponse()
                        }
                    }
                    binding.slidesError.root.isVisible = false
                    binding.newsError.root.isVisible = false
                    binding.errorTestimonials.root.isVisible = false
                }
            }
        })


        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.root.isVisible = it
        })

        return binding.root
    }

    private fun navigateToNews() {
        findNavController().navigate(R.id.nav_news_fragment)
    }
}

