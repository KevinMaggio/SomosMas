package com.alkemy.ongsomosmas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.SignUpRepository
import com.alkemy.ongsomosmas.ui.signup.SignUpFormState
import com.alkemy.ongsomosmas.ui.signup.SignUpViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: SignUpRepository
    private lateinit var viewModel: SignUpViewModel

    @Mock
    lateinit var observer: Observer<SignUpFormState>

    @Before
    fun setUp() {
        viewModel = SignUpViewModel(repository)
    }

    @Test
    fun emptyName() {
        // Given
        viewModel.signUpFormState.observeForever(observer)

        // When
        viewModel.signUpDataChanged("", "hola@hola.com", "1234hola", "1234hola")

        // Then
        verify(observer).onChanged(signUpFormStateNameError)
    }

    @Test
    fun invalidEmail() {
        // Given
        viewModel.signUpFormState.observeForever(observer)

        // When
        viewModel.signUpDataChanged("test", "hola@", "1234hola", "1234hola")

        // Then
        verify(observer).onChanged(signUpFormStateEmailError)
    }

    @Test
    fun invalidConfirmPassword() {
        // Given
        viewModel.signUpFormState.observeForever(observer)

        // When
        viewModel.signUpDataChanged("test", "hola@test.com", "1234hola", "1234")

        // Then
        verify(observer).onChanged(signUpFormStateConfirmPasswordError)
    }

    @Test
    fun validFields() {
        // Given
        viewModel.signUpFormState.observeForever(observer)

        // When
        viewModel.signUpDataChanged("test", "test@email.com", "1234hola", "1234hola")

        // Then
        verify(observer).onChanged(signUpFormStateSuccess)
    }

}