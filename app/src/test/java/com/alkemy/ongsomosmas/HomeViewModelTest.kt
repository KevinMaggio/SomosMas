package com.alkemy.ongsomosmas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.NewsResponse
import com.alkemy.ongsomosmas.data.model.TestimonialResponse
import com.alkemy.ongsomosmas.data.model.WelcomeResponse
import com.alkemy.ongsomosmas.data.news.NewsDataSource
import com.alkemy.ongsomosmas.data.news.NewsRepository
import com.alkemy.ongsomosmas.data.news.NewsService
import com.alkemy.ongsomosmas.data.testimonials.TestimonialDataSource
import com.alkemy.ongsomosmas.data.testimonials.TestimonialRepository
import com.alkemy.ongsomosmas.data.testimonials.TestimonialService
import com.alkemy.ongsomosmas.data.welcome.WelcomeDataSource
import com.alkemy.ongsomosmas.data.welcome.WelcomeRepository
import com.alkemy.ongsomosmas.data.welcome.WelcomeService
import com.alkemy.ongsomosmas.ui.home.HomeViewModel
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
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var welcomeService: WelcomeService
    lateinit var newsService: NewsService
    lateinit var testimonialService: TestimonialService

    @Mock
    lateinit var welcomeObserver: Observer<Resource<List<WelcomeResponse>>>
    lateinit var newsObserver: Observer<Resource<List<NewsResponse>>>
    lateinit var testimonialObserver: Observer<Resource<List<TestimonialResponse>>>

    private lateinit var welcomeRepository: WelcomeRepository
    private lateinit var newsRepository: NewsRepository
    private lateinit var testimonialRepository: TestimonialRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        welcomeRepository = WelcomeRepository(WelcomeDataSource(welcomeService))
        newsRepository = NewsRepository(NewsDataSource(newsService))
        testimonialRepository = TestimonialRepository(TestimonialDataSource(testimonialService))
        homeViewModel = HomeViewModel(welcomeRepository, newsRepository, testimonialRepository)
    }

    @Test
    fun getWelcomeResponseSuccess() = runBlocking {
        // Given
        BDDMockito.given(welcomeService.welcome()).willReturn(welcomeRetrofitResponseSuccess)
        homeViewModel.welcomeResult.observeForever(welcomeObserver)

        // When
        homeViewModel.getWelcomeResponse()

        // Then
        verify(welcomeObserver).apply {
            onChanged(Resource.loading())
            onChanged(welcomeResourceSuccess)
        }
    }

    @Test
    fun getWelcomeResponseError() = runBlocking {
        // Given
        BDDMockito.given(welcomeService.welcome()).willThrow(MockitoException("Error"))
        homeViewModel.welcomeResult.observeForever(welcomeObserver)

        // When
        homeViewModel.getWelcomeResponse()

        // Then
        verify(welcomeObserver).onChanged(Resource.loading())
        verify(welcomeObserver).onChanged(welcomeResourceError)
    }

    @Test
    fun getNewsSuccess() = runBlocking {
        // Given
        BDDMockito.given(newsService.news()).willReturn(newsRetrofitResponseSuccess)
        homeViewModel.newsResult.observeForever(newsObserver)

        // When
        homeViewModel.getNewsResponse()

        // Then
        verify(newsObserver).apply {
            onChanged(Resource.loading())
            onChanged(newsResourceSuccess)
        }
    }

    @Test
    fun getNewsError() = runBlocking {
        // Given
        BDDMockito.given(newsService.news()).willThrow(MockitoException("Error"))
        homeViewModel.newsResult.observeForever(newsObserver)

        // When
        homeViewModel.getNewsResponse()

        // Then
        verify(newsObserver).apply {
            onChanged(Resource.loading())
            onChanged(newsResourceError)
        }
    }

    @Test
    fun getTestimonialSuccess() = runBlocking {
        // Given
        BDDMockito.given(testimonialService.fetchTestimonials()).willReturn(
            testimonialRetrofitResponseSuccess
        )
        homeViewModel.testimonialResult.observeForever(testimonialObserver)

        // When
        homeViewModel.getTestimonialResponse()

        // Then
        verify(testimonialObserver).apply {
            onChanged(Resource.loading())
            onChanged(testimonialResourceSuccess)
        }
    }

    @Test
    fun getTestimonialError() = runBlocking {
        // Given
        BDDMockito.given(testimonialService.fetchTestimonials())
            .willThrow(MockitoException("Error"))
        homeViewModel.testimonialResult.observeForever(testimonialObserver)

        // When
        homeViewModel.getTestimonialResponse()

        // Then
        verify(testimonialObserver).apply {
            onChanged(Resource.loading())
            onChanged(testimonialResourceError)
        }
    }


}