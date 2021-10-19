package com.alkemy.ongsomosmas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.data.news.NewsDataSource
import com.alkemy.ongsomosmas.data.news.NewsRepository
import com.alkemy.ongsomosmas.data.news.NewsService
import com.alkemy.ongsomosmas.ui.news.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock lateinit var service: NewsService

    @Mock lateinit var observer: Observer<Resource<List<NewsResponse>>>

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        repository = NewsRepository(NewsDataSource(service))
        viewModel = NewsViewModel(repository)
    }

    @Test
    fun getNewsSuccess() = runBlocking {
        // Given
        BDDMockito.given(service.news()).willReturn(newsRetrofitResponseSuccess)
        viewModel.newsFragmentResult.observeForever(observer)

        // When
        viewModel.getNewsFragmentResponse()

        // Then
        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(newsResourceSuccess)
    }

    @Test
    fun getNewsError() = runBlocking {
        // Given
        BDDMockito.given(service.news()).willThrow(MockitoException("Error"))
        viewModel.newsFragmentResult.observeForever(observer)

        // When
        viewModel.getNewsFragmentResponse()

        // Then
        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(newsResourceError)
    }
}