package com.alkemy.ongsomosmas.ui.signup

import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.SignUpRepository
import com.alkemy.ongsomosmas.data.model.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class SignUpViewModel @ViewModelInject constructor(private val signUpRepository: SignUpRepository) : ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _resultOperation = MutableLiveData<Resource<RegisterResponse>>()
    val resultOperation: LiveData<Resource<RegisterResponse>> = _resultOperation

    //validate all sign up fields
    fun signUpDataChanged(name:String, email: String, password: String, confirmPassword: String) {
        if (!isNameValid(name)){
            _signUpForm.value = SignUpFormState(nameError = R.string.error_state_msg_invalid_name)
        }
        else if (!isEmailValid(email)) {
            _signUpForm.value = SignUpFormState(emailError = R.string.error_state_msg_invalid_email)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.error_state_msg_invalid_password_signUp)
        } else if (!passwordsMatch(password, confirmPassword)) {
            _signUpForm.value = SignUpFormState(confirmPasswordError = R.string.error_state_msg_mismatched_password)
        } else {
            _signUpForm.value = SignUpFormState(isDataValid = true)
        }
    }
    //validate name
    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    //validate email
    private fun isEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    //validate password
    private fun isPasswordValid(password: String): Boolean {
        val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}\$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)

        return matcher.matches()
    }

    //check if passwords are the same
    private fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun register(name: String, password: String, email: String) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) {
            signUpRepository.register(name, password, email)
        }
        _resultOperation.value = result
    }


}