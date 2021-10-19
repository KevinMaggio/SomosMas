package com.alkemy.ongsomosmas.ui.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.data.news.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsFragmentResult = MutableLiveData<Resource<List<NewsResponse>>>()
    val newsFragmentResult: LiveData<Resource<List<NewsResponse>>> = _newsFragmentResult

    init {
        getNewsFragmentResponse()
    }

    fun getNewsFragmentResponse() = viewModelScope.launch(Dispatchers.Main) {
        _newsFragmentResult.value = Resource.loading()
        val response = withContext(Dispatchers.IO) {
            newsRepository.news()
        }
        _newsFragmentResult.value = response
    }
}