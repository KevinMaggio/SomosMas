package com.alkemy.ongsomosmas.ui.login

import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.LoginRepository
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.LoginDetails
import com.alkemy.ongsomosmas.data.model.LoginResponse
import com.alkemy.ongsomosmas.data.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val preference: Preferences
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    //validate all login fields
    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.error_state_msg_invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.error_state_msg_invalid_password_signUp)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
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

    // login
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> = _loginResponse

    fun login(loginDetails: LoginDetails) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) {
            loginRepository.login(
                loginDetails.email,
                loginDetails.password
            )
        }
        if (result.isSuccessful()) {
            //result.data?.let { preference.saveUserToken(it.token) }
            preference.saveUserToken("token de prueba")
        }
        _loginResponse.value = result
    }

    fun isUserLogged(): Boolean {
        return preference.getUserToken().isNotEmpty()
    }

    fun logOut() {
        preference.clear()
    }

}