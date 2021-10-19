package com.alkemy.ongsomosmas.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.TripleLiveData
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.data.model.TestimonialResponse
import com.alkemy.ongsomosmas.data.model.WelcomeResponse
import com.alkemy.ongsomosmas.data.news.NewsRepository
import com.alkemy.ongsomosmas.data.testimonials.TestimonialRepository
import com.alkemy.ongsomosmas.data.welcome.WelcomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    private val welcomeRepository: WelcomeRepository,
    private val newsRepository: NewsRepository,
    private val testimonialRepository: TestimonialRepository,
) : ViewModel() {

    private val _welcomeResult = MutableLiveData<Resource<List<WelcomeResponse>>>()
    val welcomeResult: LiveData<Resource<List<WelcomeResponse>>> = _welcomeResult

    private val _newsResult = MutableLiveData<Resource<List<NewsResponse>>>()
    val newsResult: LiveData<Resource<List<NewsResponse>>> = _newsResult

    private val _testimonialResult = MutableLiveData<Resource<List<TestimonialResponse>>>()
    val testimonialResult: LiveData<Resource<List<TestimonialResponse>>> = _testimonialResult

    val isError =
        TripleLiveData<Resource<List<WelcomeResponse>>, Resource<List<NewsResponse>>, Resource<List<TestimonialResponse>>, Boolean>(
            _welcomeResult,
            _newsResult,
            _testimonialResult
        ) { value1, value2, value3 ->
            value1?.isError() == true && value2?.isError() == true && value3?.isError() == true
        }

    val isLoading =
        TripleLiveData<Resource<List<WelcomeResponse>>, Resource<List<NewsResponse>>, Resource<List<TestimonialResponse>>, Boolean>(
            _welcomeResult,
            _newsResult,
            _testimonialResult
        ) { value1, value2, value3 ->
            value1?.isLoading() ?: false || value2?.isLoading() ?: false || value3?.isLoading() ?: false
        }

    init {
        getWelcomeResponse()
        getNewsResponse()
        getTestimonialResponse()
    }

    fun getWelcomeResponse() = viewModelScope.launch(Dispatchers.Main) {
        _welcomeResult.value = Resource.loading()
        val result = withContext(Dispatchers.IO) {
            welcomeRepository.welcome()
        }
        _welcomeResult.value = result
    }

    fun getNewsResponse() = viewModelScope.launch(Dispatchers.Main) {
        _newsResult.value = Resource.loading()
        val result = withContext(Dispatchers.IO) {
            newsRepository.news()
        }
        _newsResult.value = result
    }

    fun getTestimonialResponse() = viewModelScope.launch(Dispatchers.Main) {
        _testimonialResult.value = Resource.loading()
        val result = withContext(Dispatchers.IO) {
            testimonialRepository.testimonials()
        }
        _testimonialResult.value = result
    }
}