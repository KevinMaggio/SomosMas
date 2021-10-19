package com.alkemy.ongsomosmas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.member.MemberDataSource
import com.alkemy.ongsomosmas.data.member.MemberRepository
import com.alkemy.ongsomosmas.data.member.MemberService
import com.alkemy.ongsomosmas.data.model.MemberResponse
import com.alkemy.ongsomosmas.ui.aboutus.AboutUsViewModel
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
class AboutUsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var service: MemberService

    @Mock
    lateinit var observer: Observer<Resource<List<MemberResponse>>>

    private lateinit var repository: MemberRepository
    private lateinit var viewModel: AboutUsViewModel

    @Before
    fun setUp() {
        repository = MemberRepository(MemberDataSource(service))
        viewModel = AboutUsViewModel(repository)
    }

    @Test
    fun getMemberResponseSuccess() = runBlocking {
        // Given
        BDDMockito.given(service.fetchMembers()).willReturn(aboutUsRetrofitResponseSuccess)
        viewModel.memberResult.observeForever(observer)

        // When
        viewModel.getMemberResponse()

        // Then
        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(aboutUsResourceSuccess)
    }

    @Test
    fun getMemberResponseError() = runBlocking {
        // Given
        BDDMockito.given(service.fetchMembers()).willThrow(MockitoException("Error"))
        viewModel.memberResult.observeForever(observer)

        // When
        viewModel.getMemberResponse()

        // Then
        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(aboutUsResourceError)
    }
}
